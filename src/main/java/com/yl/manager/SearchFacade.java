package com.yl.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.common.ErrorCode;
import com.yl.datasource.*;
import com.yl.exception.BusinessException;
import com.yl.exception.ThrowUtils;
import com.yl.model.entity.Picture;
import com.yl.model.enums.SearchTypeEnum;
import com.yl.datasource.*;
import com.yl.model.dto.search.SearchRequest;
import com.yl.model.vo.PostVO;
import com.yl.model.vo.SearchVO;
import com.yl.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 */
@Component
@Slf4j
public class SearchFacade {

    @Autowired
    private PictureDataSource pictureDataSource;

    @Autowired
    private PostDataSource postDataSource;

    @Autowired
    private UserDataSource userDataSource;

    @Autowired
    private DataSourceRegistry dataSourceRegistry;

    public SearchVO searchAll(SearchRequest searchRequest, HttpServletRequest request) throws IOException {
        String searchText = searchRequest.getSearchText();
        String type = searchRequest.getType();
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        if (searchTypeEnum == null) {
            //开启多线程
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() ->
                    pictureDataSource.doSearch(searchText, current, pageSize));

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() ->
                    postDataSource.doSearch(searchText, current, pageSize));

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() ->
                    userDataSource.doSearch(searchText, current, pageSize));

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();
            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();
                SearchVO searchVO = new SearchVO();
                searchVO.setPictureList(picturePage.getRecords());
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                return searchVO;
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
            SearchVO searchVO = new SearchVO();
            Page<?> page = dataSource.doSearch(searchText,current,pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }
}
