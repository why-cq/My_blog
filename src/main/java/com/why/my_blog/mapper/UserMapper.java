package com.why.my_blog.mapper;

import com.why.my_blog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
