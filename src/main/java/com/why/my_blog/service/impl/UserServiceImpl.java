package com.why.my_blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.why.my_blog.common.ErrorCode;
import com.why.my_blog.common.R;
import com.why.my_blog.common.dto.LoginUserVo;
import com.why.my_blog.common.dto.UserVo;
import com.why.my_blog.entity.User;

import com.why.my_blog.mapper.UserMapper;
import com.why.my_blog.service.LoginService;
import com.why.my_blog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginService loginService;


    @Override
    public User getAuthorById(Long authorId) {

        return userMapper.selectById(authorId);
    }

    @Override
    public User findUser(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account)
                .eq(User::getPassword,password)
                .select(User::getAccount,User::getId,User::getAdmin,User::getNickname)
                //增加查询效率，只查询一条
                .last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public R findUserByToken(String token) {
        /**
         * 1、token合法性校验
         * 是否为空 ，解析是否成功，redis是否存在
         * 2、如果校验失败，返回错误
         *3、如果成功，返回对应结果 LoginUserVo
         */
        //在loginservice中去检查合法性
        User user = loginService.checkToken(token);
        if (user == null) {
            return R.error(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(user.getId()).setAvatar(user.getAvatar()).setAccount(user.getAccount()).setNickname(user.getNickname());
        return R.success(loginUserVo);


    }

    @Override
    public User findUserByAccount(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount,account).last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        User User = userMapper.selectById(authorId);
        //如果
        if (User == null){
            User = new User();
            User.setId(1L);
            User.setAvatar("/static/img/logo.b3a48c0.png");
            User.setNickname("小王");
        }
        UserVo userVo = new UserVo();
        userVo.setAvatar(User.getAvatar());
        userVo.setNickname(User.getNickname());
        userVo.setId(User.getId());
        return userVo;
    }
}
