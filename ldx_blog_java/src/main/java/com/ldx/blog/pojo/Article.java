package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Integer collects;
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
    @TableField(fill = FieldFill.INSERT)
    private Long publishDate;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateDate;
    /**
     *
     */
    private String articleOmission;
    /**
     *
     */
    @TableField(fill = FieldFill.INSERT)
    private String imgUrl;
    /**
     *
     */
    private Integer views;
    /**
     *
     */
    private Integer likes;
    /**
     *
     */
    private Integer comments;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private boolean isDelete;
    @TableField(exist = false)
    private String authorAvatar;
    @TableField(exist = false)
    private String authorName;
    @TableField(exist = false)
    private List<String> categories;
    @TableField(exist = false)
    private List<String> tags;
    @TableField(exist = false)
    private String articleContent;
    @TableField(exist = false)
    private String personalBrief;

}
