package com.yl.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.common.ErrorCode;
import com.yl.common.ResultUtils;
import com.yl.exception.ThrowUtils;
import com.yl.model.dto.picture.PictureQueryRequest;
import com.yl.model.entity.Picture;
import com.yl.service.PictureService;
import com.yl.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Autowired
    private PictureService pictureService;


    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest) throws IOException {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> page = pictureService.searchPicture(pictureQueryRequest.getSearchText(), current, size);
        return ResultUtils.success(page);
    }

}
