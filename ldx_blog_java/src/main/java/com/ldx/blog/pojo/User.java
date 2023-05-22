package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    private Integer id;

    private String phone;

    private String username;

    private String password;

    private String gender;

    private String trueName;

    private String birthday;

    private String email;

    private String personalBrief;

    private String avatarImgUrl;

    private String recentlyLanded;
    private char isDelete;

    private static final long serialVersionUID = 1L;
}