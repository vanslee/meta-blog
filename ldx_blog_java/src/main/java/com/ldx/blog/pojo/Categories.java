package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Categories implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String categoryName;

    private static final long serialVersionUID = 1L;
}