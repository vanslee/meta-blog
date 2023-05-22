package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName archives
 */
@TableName(value ="archives")
@Data
public class Archives implements Serializable {
    private Integer id;

    private String archivename;

    private static final long serialVersionUID = 1L;
}