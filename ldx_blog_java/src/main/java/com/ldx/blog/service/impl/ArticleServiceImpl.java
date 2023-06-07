package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.*;
import com.ldx.blog.pojo.*;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ldx
 * @description 针对表【article】的数据库操作Service实现
 * @createDate 2023-05-21 22:17:01
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {
    private String QINIU_CDN;
    private final Integer CONTENT_LENGTH = 50;
    @Value("${website.config.cdn}")
    private String CDN_WEBSITE;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoriesMapper categoriesMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;
    @Resource
    private CategoriesServiceImpl categoriesService;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private ArticleDetailsMapper articleDetailsMapper;

    public Result<IPage<Article>> getArticlePage(Integer current, Integer size) {
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(Article::getPublishDate);
        IPage<Article> iPage = new Page<>(current, size);
        page(iPage, lqw);
        iPage.getRecords().forEach(article -> {
            article.setImgUrl(CDN_WEBSITE.concat(article.getImgUrl()));
            Long userId = article.getUserId();
            Long articleId = article.getId();
            LambdaQueryWrapper<User> lqw3 = new LambdaQueryWrapper<>();
            lqw3.eq(User::getId, userId).select(User::getAvatarImgUrl, User::getUsername, User::getPersonalBrief);
            User user = userMapper.selectOne(lqw3);
            article.setAuthorAvatar(CDN_WEBSITE.concat(user.getAvatarImgUrl()));
            article.setAuthorName(user.getUsername());
            article.setPersonalBrief(user.getPersonalBrief());
            LambdaQueryWrapper<ArticleCategory> lqw1 = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<ArticleTag> lqw2 = new LambdaQueryWrapper<>();
            lqw1.eq(ArticleCategory::getArticleId, articleId);
            lqw2.eq(ArticleTag::getArticleId, articleId);
            List<String> categories = new ArrayList<>(3);
            List<String> tags = new ArrayList<>(3);
            articleCategoryMapper.selectList(lqw1).forEach(category -> {
                categories.add(categoriesMapper.selectById(category.getCategoryId()).getCategoryName());
            });
            articleTagMapper.selectList(lqw2).forEach(tag -> {
                tags.add(tagsMapper.selectById(tag.getTagId()).getTagName());
            });
            article.setTags(tags);
            article.setCategories(categories);
        });
        return Result.success(iPage);
    }

    public boolean publishArticle(Article article) {
        try {
            String omission;
            if (article.getArticleContent().length() > CONTENT_LENGTH) {
                omission = article.getArticleContent().substring(0, CONTENT_LENGTH);
            } else {
                omission = article.getArticleContent();
            }
            article.setArticleOmission(omission);
            article.setImgUrl(article.getImgUrl());
            articleMapper.insert(article);
            Long articleId = article.getId();
            articleDetailsMapper.insert(new ArticleDetails(article.getId(), article.getArticleContent()));
            List<String> categories = article.getCategories();
            List<String> tags = article.getTags();
            List<Categories> articleCategories = categoriesMapper.selectList(null);
            List<Tags> articleTags = tagsMapper.selectList(null);
            AtomicInteger index = new AtomicInteger(0);
            articleTags.stream().forEach(item -> {
                if (index.get() == tags.size()) {
                    return;
                }
                if (!tags.contains(item.getTagName())) {
                    synchronized (index) {
                        Tags newTag = new Tags(null, tags.get(index.get()));
                        tagsMapper.insert(newTag);
                        articleTagMapper.insert(new ArticleTag(articleId, newTag.getId()));
                    }
                    index.incrementAndGet();
                }
            });
            index.set(0);
            articleCategories.stream().forEach(item -> {
                if (index.get() == categories.size()) {
                    return;
                }
                if (!categories.contains(item.getCategoryName())) {
                    synchronized (index) {
                        Categories newCategory = new Categories(null, categories.get(index.get()));
                        categoriesMapper.insert(newCategory);
                        articleCategoryMapper.insert(new ArticleCategory(articleId, newCategory.getId()));
                    }
                    index.incrementAndGet();
                }
            });
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Result<Article> getArticleById(long articleId) {
        Article article = articleMapper.selectById(articleId);
        LambdaQueryWrapper<Article> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Article::getUserId, article.getUserId());
        Long articleCount = articleMapper.selectCount(lqw1);
        // 作者文章数量
        Long userId = article.getUserId();
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.select(User::getUsername, User::getFans,User::getAvatarImgUrl).eq(User::getId, userId);
        // 作者的用户名粉丝数
        User user = userMapper.selectOne(lqw);
        user.setArticleCount(articleCount);
        ArticleDetails articleDetails = articleDetailsMapper.selectById(articleId);
        article.setArticleContent(articleDetails.getContent());
        article.setUser(user);
        return Result.success(article);
    }
}




