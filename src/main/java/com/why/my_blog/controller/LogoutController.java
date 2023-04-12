package com.why.my_blog.controller;

import com.why.my_blog.common.R;
import com.why.my_blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public R logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }




}
