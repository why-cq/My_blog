package com.why.my_blog.controller;


import com.why.my_blog.common.R;
import com.why.my_blog.common.params.PageParams;
import com.why.my_blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 返回所有的文章
     * @return
     */
    @PostMapping
    public R ListArticle(@RequestBody PageParams pageParams){
        //
        R articles = articleService.listArticlesPage(pageParams);
        return articles;
    }

    @PostMapping("/hot")
    public R hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    @PostMapping("/new")
    public R newArticle(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    @PostMapping("listArchives")
    public R listArchives(){
        return articleService.listArchives();
    }



    @PostMapping("view/{id}")
    public R findArticleById(@PathVariable("id") Long id){
        return articleService.findArticleById(id);

    }


}
