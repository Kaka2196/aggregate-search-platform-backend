package com.yl.model.dto.search;

import com.yl.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRequest extends PageRequest implements Serializable {

    private String searchText;

    private String type;

    private static final long serialVersionUID = 1L;
}
