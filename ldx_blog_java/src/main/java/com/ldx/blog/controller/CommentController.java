package com.ldx.blog.controller;

import com.ldx.blog.pojo.ArticleComment;
import com.ldx.blog.pojo.dto.CommentPage;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.impl.ArticleCommentServiceImpl;
import com.ldx.blog.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Uaena
 * @date 2023/5/19 22:51
 */
@RequestMapping("comment")
@RestController
@Slf4j
@CrossOrigin
public class CommentController {
    @Resource
    private ArticleCommentServiceImpl articleCommentService;

    @PostMapping("/comment_brief")
    public Result<List<ArticleComment>> commentBriefApi(@RequestBody CommentPage commentPage) {
        log.debug("查询简易评论:{}", commentPage);
        return articleCommentService.getCommentBriefService(commentPage);
    }

    @PutMapping("/add_article_comment")
    public Result<Boolean> addArticleCommentApi(HttpServletRequest request, @RequestBody ArticleComment articleComment) {
        log.debug("新增评论:{}", articleComment);
        String ip = IPUtil.ip(request);
        articleComment.setLocation(ip);
        return Result.success(articleCommentService.save(articleComment));
    }
}
