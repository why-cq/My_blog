package com.why.my_blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.why.my_blog.common.dto.Archives;
import com.why.my_blog.common.dto.ArticleVo;
import com.why.my_blog.common.R;
import com.why.my_blog.common.params.PageParams;
import com.why.my_blog.entity.Article;
import com.why.my_blog.mapper.ArticleMapper;
import com.why.my_blog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;


    @Override
    public R listArticlesPage(PageParams pageParams) {
        /**
         * 1、分页查询article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 构建查询条件,根据创建时间和是否置顶进行降序排列
        queryWrapper.orderByDesc(Article::getCreateDate, Article::getWeight);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //由于我们返回给前端的数据与Article不同,需要进行数据的拷贝
        List<ArticleVo> articleVos = copyList(records, true, true);
        return R.success(articleVos);


    }

    /**
     * 最热文章
     *
     * @param limit
     * @return
     */
    @Override
    public R hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return R.success(copyList(articles, false, false));
    }

    @Override
    public R newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return R.success(copyList(articles, false, false));

    }

    @Override
    public R listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return R.success(archivesList);

    }

    @Override
    public R findArticleById(Long articleId) {
        /**
         * 1. 根据id查询 文章信息
         * 2. 根据bodyId和categoryid 去做关联查询
         */
        Article article = articleMapper.selectById(articleId);
        // 使用线程池来实现文章阅读量的增加
        threadService.updateViewCount(articleMapper, article);

        ArticleVo articleVo = copy(article, true, true, true, true);


        return R.success(articleVo);
    }

    // 有可能有些请求需要作者和标签的查询
    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isAuthor, isTag));
        }

        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isAuthor, boolean isTag) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd:mm"));

        //判断是否徐亚so作者的逻辑
        if (isAuthor) {
            // 拿到作者的id
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(userService.getAuthorById(authorId).getNickname());


        }
        //判断是否需要标签的逻辑
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));


        }
        return articleVo;

    }

    private ArticleVo copy(Article article, boolean isAuthor, boolean isTag, boolean isCategory, boolean isBody) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd:mm"));

        //判断是否徐亚so作者的逻辑
        if (isAuthor) {
            // 拿到作者的id
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(userService.getAuthorById(authorId).getNickname());


        }
        //判断是否需要标签的逻辑
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));

        }

        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategorys(categoryService.findCategoryById(categoryId));

        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(categoryService.findArticleBodyById(bodyId));
        }

        return articleVo;

    }
}
