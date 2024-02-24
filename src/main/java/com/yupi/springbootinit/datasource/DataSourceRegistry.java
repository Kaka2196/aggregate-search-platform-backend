package com.yupi.springbootinit.datasource;

import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegistry {

    @Autowired
    private PictureDataSource pictureDataSource;

    @Autowired
    private PostDataSource postDataSource;

    @Autowired
    private UserDataSource userDataSource;
    private Map<String,DataSource<?>> typeDataSourceMap;
    @PostConstruct
    public void doInit(){
        typeDataSourceMap = new HashMap<>();
        typeDataSourceMap.put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        typeDataSourceMap.put(SearchTypeEnum.POST.getValue(), postDataSource);
        typeDataSourceMap.put(SearchTypeEnum.USER.getValue(), userDataSource);
    }

    public DataSource<?> getDataSourceByType(String type){
        if(typeDataSourceMap == null){
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
