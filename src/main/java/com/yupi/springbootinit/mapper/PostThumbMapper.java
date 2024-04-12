package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.entity.PostThumb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子点赞数据库操作
 *
 */
@Mapper

public interface PostThumbMapper extends BaseMapper<PostThumb> {

}




