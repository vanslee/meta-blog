package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName categories
 */
@TableName(value ="categories")
@Data
public class Categories implements Serializable {
    private Integer id;

    private String categoryname;

    private static final long serialVersionUID = 1L;
}