package com.why.my_blog.controller;


import com.why.my_blog.common.R;
import com.why.my_blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 返回最热标签
     * @return
     */
    @GetMapping("/hot")
    public R hot(){
        int limit = 5;
        return tagService.hots(limit);

    }

}
