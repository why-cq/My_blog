package com.why.my_blog.service.impl;

import com.why.my_blog.common.dto.ArticleBodyVo;
import com.why.my_blog.common.dto.CategoryVo;
import com.why.my_blog.entity.ArticleBody;
import com.why.my_blog.entity.Category;
import com.why.my_blog.mapper.ArticleBodyMapper;
import com.why.my_blog.mapper.CategoryMapper;
import com.why.my_blog.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Override
    public List<CategoryVo> findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        List<CategoryVo> list = new ArrayList<>();
        CategoryVo categoryVo = new CategoryVo();
        //因为category,categoryVo属性一样所以可以使用 BeanUtils.copyProperties
        BeanUtils.copyProperties(category,categoryVo);
        list.add(categoryVo);
        return list;
    }

    @Override
    public ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
