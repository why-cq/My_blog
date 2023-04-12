package com.why.my_blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.why.my_blog.entity.Article;
import com.why.my_blog.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ThreadService {
    //期望此操作在线程池执行不会影响原有主线程
    //这里线程池不了解可以去看JUC并发编程
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article) {
        Integer viewCounts = article.getViewCounts();
        Article articleupdate = new Article();
        articleupdate.setViewCounts(viewCounts + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId())
                //设置一个为了在多线程的环境下线程安全
                //改之前再确认这个值有没有被其他线程抢先修改，类似于CAS操作 cas加自旋，加个循环就是cas
                .eq(Article::getViewCounts,viewCounts);
        articleMapper.update(articleupdate, queryWrapper);
        try {
            Thread.sleep(5000);
            log.info("异步浏览数量增加执行成功");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
