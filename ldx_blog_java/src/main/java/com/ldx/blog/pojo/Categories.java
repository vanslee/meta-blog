package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;
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

    private String name;
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    public Categories(String name) {
        this.name = name;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Categories that = (Categories) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}