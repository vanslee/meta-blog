package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName pictures_archives
 */
@TableName(value ="pictures_archives")
@Data
public class PicturesArchives implements Serializable {
    private Integer id;

    private String picturearchive;

    private static final long serialVersionUID = 1L;
}