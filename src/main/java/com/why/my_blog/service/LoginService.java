package com.why.my_blog.service;

import com.why.my_blog.common.R;
import com.why.my_blog.common.params.Loginparams;
import com.why.my_blog.entity.User;

public interface LoginService {
    R login(Loginparams loginparams);

    User checkToken(String token);

    R logout(String token);

    R register(Loginparams loginparams);
}
