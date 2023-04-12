package com.why.my_blog.service;

import com.why.my_blog.common.R;

public interface CommentService {
    R commentsByArticleId(Long articleId);
}
