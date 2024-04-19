package com.yl.model.vo;

import com.yl.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 搜索视图
 */
@Data
public class SearchVO implements Serializable {

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

    private List<?> dataList;

    private int total;


    private static final long serialVersionUID = 1L;
}
