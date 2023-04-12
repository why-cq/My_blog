package com.why.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.why.my_blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
