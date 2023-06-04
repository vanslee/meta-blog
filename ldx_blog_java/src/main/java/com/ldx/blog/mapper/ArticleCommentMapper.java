package com.ldx.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldx.blog.pojo.ArticleComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ldx
 * @description 针对表【article_comment】的数据库操作Mapper
 * @createDate 2023-05-19 22:53:15
 * @Entity com.ldx.blog.pojo.ArticleComment
 */
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {
    /**
     * 文章根评论
     *
     * @param articleId
     * @param page      当前页码
     * @param size      当前条数
     * @return
     */
    @Select("SELECT * FROM article_comment where rootCommentId = 0 and replyCommentId = 0 and articleId = #{articleId} and isDelete = 0 order by likes desc limit #{page},#{size}")
    List<ArticleComment> getRootComments(@Param("articleId") Long articleId, @Param("page") Integer page, @Param("size") Integer size);

    /**
     * 获取根评论的子评论(前三条)
     * @param articleId 文章ID
     * @param rootId 根评论ID
     * @return
     */
    @Select("SELECT * FROM article_comment where articleId = #{articleId} and isDelete = 0 and rootCommentId =#{rootId} order by likes desc limit 0,3 ")
    List<ArticleComment> getChildrenComments(@Param("articleId") Long articleId, @Param("rootId") Long rootId);
}




