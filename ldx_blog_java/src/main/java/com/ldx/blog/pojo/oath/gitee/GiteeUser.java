package com.ldx.blog.pojo.oath.gitee;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Uaena
 * @date 2023/6/4 1:41
 */
@Getter
@Setter
public class GiteeUser {
    private String id;
    private String login;
    private String name;
    private String avatarUrl;
    private String email;
}
