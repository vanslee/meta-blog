package com.ldx.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ldx.blog.pojo.Article;
import com.ldx.blog.result.RedisKey;
import com.ldx.blog.result.Result;
import com.ldx.blog.result.ResultCodeEnum;
import com.ldx.blog.service.RedisService;
import com.ldx.blog.service.impl.ArticleDetailsServiceImpl;
import com.ldx.blog.service.impl.ArticleServiceImpl;
import com.ldx.blog.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Uaena
 * @date 2023/5/16 22:47
 */
@Slf4j
@RestController
@RequestMapping(value = "article")
@CrossOrigin
public class ArticleController {
    @Resource
    private ArticleServiceImpl articleService;
    @Resource
    private ArticleDetailsServiceImpl articleDetailsService;
    @Resource
    private RedisService redisService;

    @GetMapping("/list")
    public Result<IPage<Article>> getArticleListApi(HttpServletRequest request) {
        String cid = request.getParameter("cid");
        if (cid.equals("-1")) {
            cid = null;
        }
        ;
        Integer current = Integer.parseInt(request.getParameter("current"));
        Integer size = Integer.parseInt(request.getParameter("size"));
        String keyword = request.getParameter("keyword");
        log.debug("用户查询分页: current:{},Size:{}", current, size);
        return articleService.getArticlePage(current, size, keyword, cid, null, null);
    }

    @GetMapping("/list/{uid}")
    public Result<IPage<Article>> getArticlesByUid(HttpServletRequest request, @PathVariable long uid) {
        String size = request.getParameter("size");
        String keyword = request.getParameter("keyword");
        String desc = request.getParameter("desc");
        String current = request.getParameter("current");
        if (StringUtil.isEmpty(size) || StringUtil.isEmpty(current)) {
            return Result.fail(ResultCodeEnum.PARAM_IS_INVALID);
        }
        return articleService.getArticlesByUid(uid, keyword, desc, size, current);
    }

    /**
     * 根据article_id获取文章详情
     *
     * @param articleId
     * @return
     */
    @GetMapping("/details/{article_id}")
    public Result<Map<String, Object>> getArticleDetailsByIdApi(@PathVariable("article_id") long articleId) {
        log.debug("用户查询文章详情: {}", articleId);
        String articleViewsKey = RedisKey.ARTICLE_VIEW.concat(String.valueOf(articleId));
        Integer views = (Integer) redisService.get(articleViewsKey);
        if (Objects.isNull(views)) {
            redisService.set(articleViewsKey, 1);
        } else {
            redisService.set(articleViewsKey, views + 1);
        }
        return articleService.getArticleById(articleId);
    }
    /**
     * 根据article_id获取文章详情
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{article_id}")
    public Result<Article> getArticleById(@PathVariable("article_id") long articleId) {
        return articleService.getArticle(articleId);
    }
    @DeleteMapping("/delete/{article_id}")
    public Result<Boolean> deleteArticleById(@PathVariable("article_id") long articleId){
        try{
            return Result.success(articleService.removeById(articleId));
        }catch (RuntimeException e){
            e.printStackTrace();
            log.error("删除文章失败:{}",articleId);
            return Result.fail(ResultCodeEnum.DELETE_ARTICLE_FAIL);
        }
    }
    @DeleteMapping("/delete/batch")
    public Result<Boolean> batchDeleteArticles(@RequestBody List<Long> ids){
        try{
            return Result.success(articleService.removeBatchByIds(ids));
        }catch (RuntimeException e){
            log.error("删除文章失败:{}",ids);
            return Result.fail(ResultCodeEnum.DELETE_ARTICLE_FAIL);
        }
    }

    /**
     * 发布文章
     */
    @PutMapping("/publish")
    public Result<ResultCodeEnum> publishArticleApi(@Valid @RequestBody Article article) {
        boolean save = articleService.publishArticle(article);
        if (save) {
            return Result.success(ResultCodeEnum.PUBLISH_SUCCESS);
        } else {
            return Result.fail(ResultCodeEnum.PUBLISH_FAIL);
        }
    }
    /**
     * 修改文章
     */
    @PutMapping("/update")
    public Result<ResultCodeEnum> updateArticleApi(@Valid @RequestBody Article article) {
        boolean save = articleService.updateArticle(article);
        if (save) {
            return Result.success(ResultCodeEnum.PUBLISH_SUCCESS);
        } else {
            return Result.fail(ResultCodeEnum.PUBLISH_FAIL);
        }
    }
}
