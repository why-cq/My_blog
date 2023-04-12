package com.why.my_blog.service;

import com.why.my_blog.common.dto.ArticleBodyVo;
import com.why.my_blog.common.dto.CategoryVo;

import java.util.List;

public interface CategoryService {

    List<CategoryVo> findCategoryById(Long categoryId);

    ArticleBodyVo findArticleBodyById(Long bodyId);
}
