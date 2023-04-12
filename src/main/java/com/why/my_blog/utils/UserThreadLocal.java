package com.why.my_blog.utils;

import com.why.my_blog.entity.User;

public class UserThreadLocal {
    private UserThreadLocal(){};

    //线程变量隔离
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

    public static void put(User user){
        LOCAL.set(user);
    }

    public static User get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }



}
