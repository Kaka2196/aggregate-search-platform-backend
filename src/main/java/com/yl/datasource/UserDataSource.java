package com.yl.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.model.dto.user.UserQueryRequest;
import com.yl.model.vo.UserVO;
import com.yl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户服务实现
 */
@Component
@Slf4j
public class UserDataSource implements DataSource<UserVO> {

    @Autowired
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setPageSize((int) pageSize);
        userQueryRequest.setCurrent((int) pageNum);
        return userService.listUserVOByPage(userQueryRequest);
    }
}
