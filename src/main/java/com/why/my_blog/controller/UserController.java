package com.why.my_blog.controller;


import com.why.my_blog.common.R;
import com.why.my_blog.service.UserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/currenUser")
    public R currenUser(@RequestHeader("Authorization")String token){
        return userService.findUserByToken(token);

    }

}
