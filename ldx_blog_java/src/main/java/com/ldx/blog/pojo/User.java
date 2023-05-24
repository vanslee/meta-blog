package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Uaena
 * @TableName user
 */
@TableName(value ="user")
@Data
@Accessors(chain = true)
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
    private String ip;

    private static final long serialVersionUID = 1L;
}