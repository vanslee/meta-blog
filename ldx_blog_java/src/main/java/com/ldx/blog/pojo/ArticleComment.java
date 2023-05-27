package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;

/**
* @author Uaena
* @TableName article_comment
*/
@Data
public class ArticleComment implements Serializable {

    /**
    * 评论id
    */
    private Long id;
    /**
    * 评论的文章id
    */
    private Long articleId;
    /**
    * 评论内容
    */
    private String content;
    /**
    * 评论时间
    */
    private Long createTime;
    /**
    * 是否删除
    */
    private Integer isDelete;
    /**
    * 用户id
    */
    private Long userid;
    /**
    * 评论点赞数
    */
    private Object commentLikes;
    /**
    * 根评论ID
    */
    private Long rootCommentId;
    /**
    * 回复评论id
    */
    private Long replyCommentId;
    /**
    * 评论修改时间
    */
    private Integer updateTime;
    /**
    * 是否置顶
    */
    private Integer isTop;
    /**
    * 评论人昵称
    */
    private String userNick;
    /**
     * 评论人头像
     */
    private String userAvatar;
    /**
     * 子评论列表
     */
    @TableField(exist = false)
    private IPage<ArticleComment> childrens;


}
