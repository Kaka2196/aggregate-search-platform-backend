package com.yl.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.model.dto.post.PostQueryRequest;
import com.yl.model.vo.PostVO;
import com.yl.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务实现
 */
@Component
@Slf4j
public class PostDataSource  implements DataSource<PostVO> {

    @Resource
    private PostService postService;



    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setPageSize((int) pageSize);
        postQueryRequest.setCurrent((int) pageNum);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (servletRequestAttributes != null) {
            request = servletRequestAttributes.getRequest();
        }
        //return postService.listPostVOByPage(postQueryRequest,request);
        return postService.getPostVOPage(postService.searchFromEs(postQueryRequest),request);
    }
}




