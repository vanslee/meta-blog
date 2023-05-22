package com.ldx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.ArticleCommentMapper;
import com.ldx.blog.pojo.ArticleComment;
import com.ldx.blog.pojo.dto.CommentPage;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.ArticleCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author ldx
* @description 针对表【article_comment】的数据库操作Service实现
* @createDate 2023-05-19 22:53:15
*/
@Service
@Transactional
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment>
    implements ArticleCommentService{
    @Resource
    private ArticleCommentMapper articleCommentMapper;
    public Result<IPage<ArticleComment>> getCommentBriefService(CommentPage commentPage) {
        LambdaQueryWrapper<ArticleComment> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ArticleComment::getRootCommentId,0)
                .eq(ArticleComment::getReplyCommentId,0)
                .eq(ArticleComment::getIsDelete,0)
                .eq(ArticleComment::getArticleId,commentPage.getArticleId());
        IPage<ArticleComment> iPage = new Page<>(commentPage.getCurrent(),commentPage.getSize());
        page(iPage,lqw);
        iPage.getRecords().forEach(rootComment->{
            LambdaQueryWrapper<ArticleComment> commentLqw = new LambdaQueryWrapper<>();
            commentLqw.eq(ArticleComment::getArticleId,commentPage.getArticleId())
                    .eq(ArticleComment::getRootCommentId,rootComment.getId())
                    .eq(ArticleComment::getIsDelete,0)
                    .orderByDesc(ArticleComment::getCommentLikes);
            IPage<ArticleComment> commentIPage = new Page<>(0,3);
            page(commentIPage,commentLqw);
           rootComment.setChildrens(commentIPage);
       });
       return Result.success(iPage);
    }
}




