package com.yupi.springbootinit.model.dto.search;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRequest extends PageRequest implements Serializable {

    private String searchText;

    private static final long serialVersionUID = 1L;
}
