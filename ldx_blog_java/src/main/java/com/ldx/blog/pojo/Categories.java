package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Uaena
 * @TableName categories
 */
@TableName(value ="categories")
@Getter
@Setter
public class Categories implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String categoryName;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    public Categories(String categoryName) {
        this.categoryName = categoryName;
    }

    private static final long serialVersionUID = 1L;
}