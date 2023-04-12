package com.why.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.my_blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
