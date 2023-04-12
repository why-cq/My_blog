package com.why.my_blog.service;

import com.why.my_blog.common.R;
import com.why.my_blog.common.dto.TagVo;
import com.why.my_blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author why
 * @since 2023-04-09
 */
public interface TagService extends IService<Tag> {

    List<TagVo> findTagsByArticleId(Long articleId);

    R hots(int limit);
}
