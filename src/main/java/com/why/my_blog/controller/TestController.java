package com.why.my_blog.controller;

import com.why.my_blog.common.R;
import com.why.my_blog.entity.User;
import com.why.my_blog.utils.UserThreadLocal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping

    public R test(){
        User user = UserThreadLocal.get();
        System.out.println(user);
        return R.success(null);
    }
}
