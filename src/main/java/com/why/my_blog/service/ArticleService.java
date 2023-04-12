package com.why.my_blog.service;

import com.why.my_blog.common.R;
import com.why.my_blog.common.params.PageParams;

import com.baomidou.mybatisplus.extension.service.IService;
import com.why.my_blog.entity.Article;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
public interface ArticleService extends IService<Article> {

    R listArticlesPage(PageParams pageParams);

    R hotArticle(int limit);

    R newArticles(int limit);

    R listArchives();

    R findArticleById(Long id);
}
