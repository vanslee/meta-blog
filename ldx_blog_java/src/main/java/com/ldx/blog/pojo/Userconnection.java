package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName UserConnection
 */
@TableName(value ="UserConnection")
@Data
public class Userconnection implements Serializable {
    private String userid;

    private String providerid;

    private String provideruserid;

    private Integer rank;

    private String displayname;

    private String profileurl;

    private String imageurl;

    private String accesstoken;

    private String secret;

    private String refreshtoken;

    private Long expiretime;

    private static final long serialVersionUID = 1L;
}