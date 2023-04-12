package com.why.my_blog.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Accessors(chain = true)
@Data
public class LoginUserVo implements Serializable {

    //与页面交互

    private Long id;

    private String account;

    private String nickname;

    private String avatar;
}
