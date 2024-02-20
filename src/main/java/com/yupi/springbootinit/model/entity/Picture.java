package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 图片
 */
@Data
@TableName(value = "picture")
public class Picture {
    private Long id;

    private String title;

    private String url;

}
