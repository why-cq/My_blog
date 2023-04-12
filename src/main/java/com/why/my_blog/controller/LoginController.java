package com.why.my_blog.controller;


import com.why.my_blog.common.R;
import com.why.my_blog.common.params.Loginparams;
import com.why.my_blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public R login(@RequestBody Loginparams loginparams){
        return loginService.login(loginparams);

    }


}
