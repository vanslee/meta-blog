package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName friendlink
 */
@TableName(value ="friendlink")
@Data
public class Friendlink implements Serializable {
    private Integer id;

    private String blogger;

    private String url;

    private static final long serialVersionUID = 1L;
}