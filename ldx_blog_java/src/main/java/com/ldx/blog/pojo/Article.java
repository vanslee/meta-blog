package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Uaena
 * @TableName article
 */
@Data
public class Article implements Serializable {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long userId;
    /**
     *
     */
    private String originalUrl;
    /**
     *
     */
    private String articleTitle;
    /**
     *
     */
    private Long publishDate;
    /**
     *
     */
    private Long updateDate;
    /**
     *
     */
    private String articleOmission;
    /**
     *
     */
    private String imgUrl;
    /**
     *
     */
    private Object views;
    /**
     *
     */
    private Object likes;
    /**
     *
     */
    private Object comments;
    private char isDelete;
    @TableField(exist = false)
    private String authorAvatar;
    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private List<String> categories;
    @TableField(exist = false)
    private List<String> tags;


}
