package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.pojo.Article;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.ArticleService;
import com.ldx.blog.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author ldx
* @description 针对表【article】的数据库操作Service实现
* @createDate 2023-05-21 22:17:01
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    public Result<IPage<Article>> getArticlePage(Integer current, Integer size) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getIsDelete,0).orderByDesc(Article::getPublishDate);
        IPage<Article> iPage = new Page<>(current,size);
        return Result.success(page(iPage, lqw));
    }
}




