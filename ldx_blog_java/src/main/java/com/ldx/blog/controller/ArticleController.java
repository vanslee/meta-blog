package com.ldx.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ldx.blog.pojo.Article;
import com.ldx.blog.pojo.ArticleDetails;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.impl.ArticleDetailsServiceImpl;
import com.ldx.blog.service.impl.ArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Uaena
 * @date 2023/5/16 22:47
 */
@Slf4j
@RestController
@RequestMapping(value = "article")
public class ArticleController {
    @Resource
    private ArticleServiceImpl articleService;
    @Resource
    private ArticleDetailsServiceImpl articleDetailsService;

    @GetMapping("/list")
    public Result<IPage<Article>> getArticleListApi(@RequestParam("current") Integer current, @RequestParam("size") Integer size) {
        log.debug("用户查询分页: current:{},Size:{}",current,size);
       return articleService.getArticlePage(current,size);
    }

    /**
     * 根据article_id获取文章详情
     * @param article_id
     * @return
     */
    @GetMapping("/details/{article_id}")
    public Result<ArticleDetails> getArticleDetailsByIdApi(@PathVariable long article_id) {
        log.debug("用户查询文章详情: {}",article_id);
        return Result.success(articleDetailsService.getById(article_id));
    }
//    /**
//     * 发布文章
//     */
//    @PutMapping("/article/publish")
//    public Result<ResultCodeEnum> publishArticleApi(@RequestBody Article article){
//        boolean save = articleService.publishArticle(article);
//        if (save){
//            return Result.success(ResultCodeEnum.PUBLISH_SUCCESS);
//        }else {
//            return Result.fail(ResultCodeEnum.PUBLISH_FAIL);
//        }
//    }
}
