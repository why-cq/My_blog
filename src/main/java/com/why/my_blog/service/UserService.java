package com.why.my_blog.service;

import com.why.my_blog.common.R;
import com.why.my_blog.common.dto.UserVo;
import com.why.my_blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
public interface UserService extends IService<User> {


    User getAuthorById(Long authorId);

    User findUser(String account, String password);

    R findUserByToken(String token);

    User findUserByAccount(String account);

    UserVo findUserVoById(Long authorId);
}
