package com.why.my_blog.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R implements Serializable {
    private boolean success;

    private Integer code;

    private String msg;

    private Object data;

    public static R success(Object data) {
        return new R(true,200,"success",data);
    }
    public static R error(Integer code, String msg) {
        return new R(false,code,msg,null);
    }
}
