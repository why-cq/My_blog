package com.why.my_blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.why.my_blog.common.ErrorCode;
import com.why.my_blog.common.R;
import com.why.my_blog.common.params.Loginparams;
import com.why.my_blog.entity.User;
import com.why.my_blog.service.LoginService;
import com.why.my_blog.service.UserService;
import com.why.my_blog.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //加密盐用于加密
    private static final String slat = "mszlu!@#";

    @Override
    public R login(Loginparams loginparams) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在 ，使用jwt 生成token 返回给前端
         * 5. token放入redis当中，redis  token：user信息 设置过期时间（相比来说session会给服务器产生压力，这么做也是为了实现jwt的续签）
         *  (登录认证的时候 先认证token字符串是否合法，去redis认证是否存在)
         */
        String account = loginparams.getAccount();
        String password = loginparams.getPassword();
        if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
            return R.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        // 对pwd进行加密存入数据库库中使用md5加上盐
        String pwd = DigestUtils.md5DigestAsHex((password + slat).getBytes());
        //数据库中查找用户
        User user = userService.findUser(account, pwd);
        //不存在返回错误信息
        if (user == null) {
            return R.error(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，返回token和redis中
        // JSON.toJSONString 用法
        //过期时间是一百天
        String token = JWTUtils.createToken(user.getId());
        log.info(token);
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
        return R.success(token);


    }

    @Override
    public User checkToken(String token) {
        // 检查token合法性
        // 1.解析失败
        if (!StringUtils.hasText(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        // 2. 解析成功
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        // 3.解析会User对象
        User user = JSON.parseObject(userJson, User.class);
        return user;

    }

    @Override
    public R logout(String token) {
        //后端直接删除redis中的token
        redisTemplate.delete("TOKEN_" + token);
        return R.success(null);

    }

    @Override

    public R register(Loginparams loginparams) {
        /**
         * 1. 判断参数 是否合法
         * 2. 判断账户是否存在，存在 返回账户已经被注册
         * 3. 不存在，注册用户
         * 4. 生成token
         * 5. 存入redis 并返回
         * 6. 注意 加上事务，一旦中间的任何过程出现问题，注册的用户 需要回滚
         */
        String account = loginparams.getAccount();
        String password = loginparams.getPassword();
        String nickname = loginparams.getNickname();
        if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
            return R.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        User user = userService.findUserByAccount(account);
        if (user != null) {
            return R.error(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        user = new User();
        user.setNickname(nickname);
        user.setAccount(account);
        user.setPassword(DigestUtils.md5DigestAsHex((password + slat).getBytes()));
        user.setCreateDate(System.currentTimeMillis());
        user.setLastLogin(System.currentTimeMillis());
        user.setAvatar("/static/img/logo.b3a48c0.png");
        user.setAdmin(true); //1 为true
        user.setDeleted(false); // 0 为false
        user.setSalt("");
        user.setStatus("");
        user.setEmail("");
        userService.save(user);

        //token
        String token = JWTUtils.createToken(user.getId());

        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);

        return R.success(token);


    }
}
