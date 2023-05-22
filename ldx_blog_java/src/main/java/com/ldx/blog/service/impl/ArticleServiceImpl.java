package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.*;
import com.ldx.blog.pojo.Article;
import com.ldx.blog.pojo.ArticleCategory;
import com.ldx.blog.pojo.ArticleTag;
import com.ldx.blog.pojo.User;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author ldx
* @description 针对表【article】的数据库操作Service实现
* @createDate 2023-05-21 22:17:01
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Value("${website.config.cdn}")
    private String CDN_WEBSITE;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private CategoriesMapper categoriesMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;

    public Result<IPage<Article>> getArticlePage(Integer current, Integer size) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getIsDelete,0).orderByDesc(Article::getPublishDate);
        IPage<Article> iPage = new Page<>(current,size);
        page(iPage, lqw);
        iPage.getRecords().forEach(article->{
            article.setImgUrl(CDN_WEBSITE.concat(article.getImgUrl()));
            Long userId = article.getUserId();
            Long articleId = article.getId();
            LambdaQueryWrapper<User> lqw3 = new LambdaQueryWrapper<>();
            lqw3.eq(User::getId,userId).select(User::getAvatarImgUrl,User::getUsername);
            User user = userMapper.selectOne(lqw3);
            article.setAuthorAvatar(CDN_WEBSITE.concat(user.getAvatarImgUrl()));
            article.setAuthorName(user.getUsername());
            LambdaQueryWrapper<ArticleCategory> lqw1 = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<ArticleTag> lqw2 = new LambdaQueryWrapper<>();
            lqw1.eq(ArticleCategory::getArticleId,articleId);
            lqw2.eq(ArticleTag::getArticleId,articleId);
            List<String> categories = new ArrayList<>(3);
            List<String> tags = new ArrayList<>(3);
            articleCategoryMapper.selectList(lqw1).forEach(category->{
                categories.add(categoriesMapper.selectById(category.getCategoryId()).getCategoryname()) ;
            });
            articleTagMapper.selectList(lqw2).forEach(tag->{
                tags.add(tagsMapper.selectById(tag.getTagId()).getTagName());
            });
            article.setTags(tags);
            article.setCategories(categories);
        });
        return Result.success(iPage);
    }
}




