package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName pictures
 */
@TableName(value ="pictures")
@Data
public class Pictures implements Serializable {
    private Integer id;

    private String picturename;

    private String pictureurl;

    private String picturedate;

    private String picturedesc;

    private static final long serialVersionUID = 1L;
}