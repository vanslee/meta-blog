package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName daily_speech
 */
@TableName(value ="daily_speech")
@Data
public class DailySpeech implements Serializable {
    private Integer id;

    private String content;

    private String mood;

    private String picsurl;

    private Date publishdate;

    private static final long serialVersionUID = 1L;
}