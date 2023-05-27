package com.ldx.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ldx.blog.pojo.ArticleComment;
import com.ldx.blog.pojo.dto.CommentPage;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.impl.ArticleCommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Uaena
 * @date 2023/5/19 22:51
 */
@RequestMapping("comment")
@RestController
@Slf4j
public class CommentController {
    @Resource
    private ArticleCommentServiceImpl articleCommentService;

    @PostMapping("/comment_brief")
    public Result<IPage<ArticleComment>> commentBriefApi(@RequestBody CommentPage commentPage) {
        log.debug("查询简易评论:{}", commentPage);
        return articleCommentService.getCommentBriefService(commentPage);
    }

    @PutMapping("/add_article_comment")
    public Result<Boolean> addArticleCommentApi(@RequestBody ArticleComment articleComment) {
        log.debug("新增评论:{}",articleComment);
        return Result.success(articleCommentService.save(articleComment));
    }
}
