package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName privateword
 */
@TableName(value ="privateword")
@Data
public class Privateword implements Serializable {
    private Integer id;

    private String privateword;

    private String publisherid;

    private String replierid;

    private String replycontent;

    private String publisherdate;

    private static final long serialVersionUID = 1L;
}