package com.why.my_blog.controller;

import com.why.my_blog.common.R;
import com.why.my_blog.common.params.Loginparams;
import com.why.my_blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public R register(@RequestBody Loginparams loginparams){
        return loginService.register(loginparams);
    }
}
