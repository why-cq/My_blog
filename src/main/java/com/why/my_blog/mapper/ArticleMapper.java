package com.why.my_blog.mapper;

import com.why.my_blog.common.dto.Archives;
import com.why.my_blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();
}
