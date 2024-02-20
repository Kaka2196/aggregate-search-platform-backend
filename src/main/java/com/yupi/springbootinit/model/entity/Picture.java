package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 图片
 */
@Data
@TableName(value = "picture")
public class Picture implements Serializable {


    private static final long serialVersionUID = 1L;

    private String title;

    private String url;

}
