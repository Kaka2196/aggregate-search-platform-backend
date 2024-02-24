package com.yupi.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.datasource.*;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
