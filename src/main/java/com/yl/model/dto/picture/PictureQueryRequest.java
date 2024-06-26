package com.yl.model.dto.picture;

import com.yl.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class PictureQueryRequest extends PageRequest implements Serializable {

    //搜索词
    private String searchText;

    private static final long serialVersionUID = 1L;
}
