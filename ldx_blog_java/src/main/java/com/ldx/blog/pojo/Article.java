package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
     * markdown文件
     */

    private String mdUrl;
    /**
     *
     */
    private String originalUrl;
    /**
     *
     */
    @NotBlank(message = "文章标题不能为空")
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
    @TableField(fill = FieldFill.INSERT)
    @NotBlank(message = "请上传文章封面")
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
    @Size(min = 1,message = "至少选择一个分类")
    private List<String> categories;
    @TableField(exist = false)
    @Size(min = 1,message = "至少选择一个标签")
    private List<String> tags;
    @TableField(exist = false)
    @NotBlank(message = "文章内容不能为空")
    private String articleContent;
    @TableField(exist = false)
    private String personalBrief;

}
