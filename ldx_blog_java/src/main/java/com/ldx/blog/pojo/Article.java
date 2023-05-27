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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer isDelete;
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


}
