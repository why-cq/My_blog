package com.why.my_blog.controller;

import com.why.my_blog.common.R;
import com.why.my_blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public R comments(@PathVariable("id")Long articleId){
        return commentService.commentsByArticleId(articleId);

    }
}
