package com.ldx.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.*;
import com.ldx.blog.pojo.*;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.ArticleService;
import com.ldx.blog.utils.FileUtil;
import com.ldx.blog.utils.QiNiuYunOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @author ldx
 * @description 针对表【article】的数据库操作Service实现
 * @createDate 2023-05-21 22:17:01
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {
    private String QINIU_CDN;
    private final Integer CONTENT_LENGTH = 20;
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
    @Resource
    private QiNiuYunOssUtil ossUtil;

    public Result<IPage<Article>> getArticlePage(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        String sort = request.getParameter("sort");
        String keyword = request.getParameter("keyword");
        Integer size = Integer.parseInt(request.getParameter("size"));
        Integer current = Integer.parseInt(request.getParameter("current"));
        log.debug("用户查询分页: current:{},Size:{}", current, size);
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        if (!Objects.isNull(uid)) {
            lqw.eq(Article::getUserId, uid);
        }
        if (!Objects.isNull(keyword)) {
            lqw.like(Article::getArticleTitle, keyword);
        }
        if (!Objects.isNull(sort)) {
            lqw.orderByDesc(sort.equals("desc"), Article::getPublishDate);
        }
        IPage<Article> iPage = new Page<>(current, size);
        page(iPage, lqw);
        iPage.getRecords().forEach(article -> {
            article.setMdUrl(CDN_WEBSITE.concat(article.getMdUrl()));
            article.setImgUrl(CDN_WEBSITE.concat(article.getImgUrl()));
            Long userId = article.getUserId();
            Long articleId = article.getId();
            LambdaQueryWrapper<User> lqw3 = new LambdaQueryWrapper<>();
            lqw3.eq(User::getId, userId).select(User::getAvatarImgUrl, User::getUsername, User::getPersonalBrief);
            User user = userMapper.selectOne(lqw3);
            article.setAuthorAvatar(CDN_WEBSITE.concat(user.getAvatarImgUrl()));
            article.setAuthorName(user.getUsername());
            article.setPersonalBrief(user.getPersonalBrief());
            article.setTags(getTags(articleId));
            article.setCategories(getCategories(articleId));
        });
        return Result.success(iPage);
    }

    public boolean publishArticle(Article article) {
        try {
            File file = FileUtil.generateMarkdown(article.getArticleTitle(), article.getArticleContent());
            String url = ossUtil.uploadMarkdown(file, StpUtil.getLoginId() + "/");
            article.setMdUrl(url);
            articleMapper.insert(article);
            Long articleId = article.getId();
            setTags(article.getTags(), articleId);
            setCategories(article.getCategories(), articleId);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateArticle(Article article) {
        try {
            File file = FileUtil.generateMarkdown(article.getArticleTitle(), article.getArticleContent());
            String url = ossUtil.uploadMarkdown(file, StpUtil.getLoginId() + "/");
            article.setMdUrl(url);
            article.setUpdateDate(System.currentTimeMillis() / 1000);
            articleMapper.updateById(article);
            setTags(article.getTags(), article.getId());
            setCategories(article.getCategories(), article.getId());
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Result<Map<String, Object>> getArticleById(long articleId) {
        Map<String, Object> resultMap = new HashMap<>(2);
        Article article = articleMapper.selectById(articleId);
        article.setMdUrl(CDN_WEBSITE.concat(article.getMdUrl()));
        article.setImgUrl(CDN_WEBSITE.concat(article.getImgUrl()));
        LambdaQueryWrapper<Article> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Article::getUserId, article.getUserId());
        Long articleCount = articleMapper.selectCount(lqw1);
        // 作者文章数量
        Long userId = article.getUserId();
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.select(User::getUsername, User::getFans, User::getAvatarImgUrl, User::getId).eq(User::getId, userId);
        // 作者的用户名粉丝数
        User user = userMapper.selectOne(lqw);
        user.setAvatarImgUrl(CDN_WEBSITE.concat(user.getAvatarImgUrl()));
        user.setArticleCount(articleCount);
        resultMap.put("article", article);
        resultMap.put("author", user);
        return Result.success(resultMap);
    }

    public Result<Article> getArticle(long articleId) {
        Article article = articleMapper.selectById(articleId);
        List<Categories> categories = getCategories(articleId);
        List<Tags> tags = getTags(articleId);
        article.setCategories(categories);
        article.setTags(tags);
        article.setMdUrl(CDN_WEBSITE.concat(article.getMdUrl()));
        article.setImgUrl(CDN_WEBSITE.concat(article.getImgUrl()));
        LambdaQueryWrapper<Article> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Article::getUserId, article.getUserId());
        // 作者文章数量
        Long userId = article.getUserId();
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.select(User::getUsername, User::getAvatarImgUrl).eq(User::getId, userId);
        // 作者的用户名粉丝数
        User user = userMapper.selectOne(lqw);
        article.setUsername(user.getUsername());
        article.setAvatar(CDN_WEBSITE.concat(user.getAvatarImgUrl()));
        return Result.success(article);
    }

    public Result<IPage<Article>> getArticlesByCid(long articleId, Integer current, Integer size) {
        try {
            LambdaQueryWrapper<ArticleCategory> lqw = new LambdaQueryWrapper<>();
            lqw.eq(ArticleCategory::getCategoryId, articleId);
            List<ArticleCategory> articleCategories = articleCategoryMapper.selectList(lqw);
            List<Long> articleIds = new ArrayList<>(articleCategories.size());
            articleCategories.forEach(item -> {
                articleIds.add(item.getArticleId());
            });
            IPage<Article> iPage = new Page<>(current, size);
            LambdaQueryWrapper<Article> lqw1 = new LambdaQueryWrapper<>();
            lqw1.in(Article::getId, articleIds);
            page(iPage, lqw1);
            return Result.success(iPage);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.fail();
        }
    }

//    /**
//     * 根据uid获得文章信息
//     *
//     * @param uid
//     * @param keyword
//     * @param desc
//     * @param size
//     * @param current
//     * @return
//     */
//    public Result<IPage<Article>> getArticlesByUid(long uid, String keyword, String desc, String size, String current) {
//        if (Objects.isNull(desc)) {
//            desc = "DESC";
//        }
//        return getArticlePage(Integer.parseInt(current), Integer.parseInt(size), keyword, null, uid, desc);
//    }

    public List<Categories> getCategories(long articleId) {
        LambdaQueryWrapper<ArticleCategory> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleCategory::getArticleId, articleId);
        List<ArticleCategory> articleCategories = articleCategoryMapper.selectList(lqw);
        List<Integer> cids = new ArrayList<>(articleCategories.size());
        articleCategories.forEach(cid -> {
            cids.add(cid.getCategoryId());
        });
        if (CollectionUtils.isEmpty(cids)) {
            return new ArrayList<>(0);
        } else {
            List<Categories> categories = categoriesMapper.selectBatchIds(cids);
            return categories;
        }


    }

    public List<Tags> getTags(long articleId) {
        LambdaQueryWrapper<ArticleTag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleTag::getArticleId, articleId);
        List<ArticleTag> articleTags = articleTagMapper.selectList(lqw);
        List<Integer> tids = new ArrayList<>(articleTags.size());
        articleTags.forEach(tid -> {
            tids.add(tid.getTagId());
        });
        if (CollectionUtils.isEmpty(tids)) {
            return new ArrayList<>(0);
        } else {
            List<Tags> tags = tagsMapper.selectBatchIds(tids);
            return tags;
        }
    }

    public void setTags(List<Tags> tags, Long articleId) {
        LambdaQueryWrapper<Tags> lqw = new LambdaQueryWrapper<>();
        List<Tags> allTags = tagsMapper.selectList(lqw);
        List<Tags> tagsInDb = new ArrayList<>(allTags.size());
        allTags.forEach(tag -> {
            tagsInDb.add(tag);
        });
        tags.forEach(tag -> {
//            tagName = tagName.trim();
            if (!tagsInDb.contains(tag)) {
                tag.setCreateTime(System.currentTimeMillis() / 1000);
                tagsMapper.insert(tag);
                tag.setName(tag.getName().trim());
                ArticleTag articleTag = new ArticleTag(articleId, tag.getId());
                articleTagMapper.insert(articleTag);
            }
        });

    }

    public void setCategories(List<Categories> categories, Long articleId) {
        LambdaQueryWrapper<Categories> lqw = new LambdaQueryWrapper<>();
        lqw.select(Categories::getName);
        List<Categories> allCategories = categoriesMapper.selectList(lqw);
        List<Categories> categoriesInDb = new ArrayList<>(allCategories.size());
        allCategories.forEach(category -> {
            categoriesInDb.add(category);
        });
        categories.forEach(category -> {
            if (!categoriesInDb.contains(category)) {
                category.setCreateTime(System.currentTimeMillis() / 1000);
                categoriesMapper.insert(category);
                category.setName(category.getName().trim());
                ArticleCategory articleTag = new ArticleCategory(articleId, category.getId());
                articleCategoryMapper.insert(articleTag);
            }
        });
    }


}




