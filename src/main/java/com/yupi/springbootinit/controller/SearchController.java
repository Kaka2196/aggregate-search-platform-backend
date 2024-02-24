package com.yupi.springbootinit.controller;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.manager.SearchFacade;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 统一查询接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Autowired
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws IOException {
       return ResultUtils.success(searchFacade.searchAll(searchRequest,request));
    }
}
