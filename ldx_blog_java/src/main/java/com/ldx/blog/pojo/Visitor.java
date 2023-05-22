package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName visitor
 */
@TableName(value ="visitor")
@Data
public class Visitor implements Serializable {
    private Integer id;

    private Long visitornum;

    private String pagename;

    private static final long serialVersionUID = 1L;
}