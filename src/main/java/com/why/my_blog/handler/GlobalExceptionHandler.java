package com.why.my_blog.handler;

import com.why.my_blog.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R dpExcepton(Exception ex){
        //e.printStackTrace();是打印异常的堆栈信息，指明错误原因，
        // 其实当发生异常时，通常要处理异常，这是编程的好习惯，
        // 所以e.printStackTrace()可以方便你调试程序！
        ex.printStackTrace();
        return R.error(-999, "系统异常");

    }


}
