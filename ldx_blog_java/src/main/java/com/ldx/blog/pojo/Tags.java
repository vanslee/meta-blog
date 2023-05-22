package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName tags
 */
@TableName(value ="tags")
@Data
public class Tags implements Serializable {
    private Integer id;

    private String tagname;

    private Integer tagsize;

    private static final long serialVersionUID = 1L;
}