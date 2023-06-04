package com.ldx.blog.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Uaena
 * @TableName user
 */
@TableName(value = "user")
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;


    private String phone;
    @Size(min = 3, max = 20, message = "用户名必须在3~8之间")
    private String username;
    @Size(min = 8, max = 20, message = "密码必须在6~20之间")
    private String password;
    @TableField(fill = FieldFill.INSERT)
    private boolean gender;

    private String trueName;

    private String birthday;

    private String email;
    private Integer fans;
    private String personalBrief;
    @TableField(exist = false)
    private Long articleCount;
    private String avatarImgUrl;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private long recentlyLanded;
    @TableField(fill = FieldFill.INSERT)
    private boolean isDelete;
    private String ip;
    private String unionId;

    private static final long serialVersionUID = 1L;
}