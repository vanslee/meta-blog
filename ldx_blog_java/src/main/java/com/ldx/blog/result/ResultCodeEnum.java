package com.ldx.blog.result;

import lombok.Getter;

/**
 * @author LoneWalker
 */

@Getter
public enum ResultCodeEnum {

    /**
     * success
     */
    SUCCESS(200, "操作成功"),
    UPLOAD_FILE_SUCCESS(200, "上传成功"),
    PUBLISH_SUCCESS(200, "发布文章成功"),
    /**
     * fail
     */
    FAIL(-1, "操作失败"),
    NOT_LOGIN(5000, "身份已过期"),
    UNKNOWN_ERROR(301, "未知错误"),
    UPLOAD_FILE_ERROR(505, "上传失败"),
    PUBLISH_FAIL(506, "发布文章失败"),
    /**
     * 参数错误：1001-1999
     */
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_TYPE_ERROR(1002, "参数类型错误"),

    LOGIN_PARAM_NULL(500, "缺少用户名或密码"),
    LOGIN_ERROR(500, "用户名或密码错误"),
    LOGIN_SUCCESS(200, "登录成功"), LOGOUT_SUCCESS(200, "退出成功"),
    USER_HAS_EXIST(503, "用户已存在,去登录"), REGISTRY_SUCCESS(200, "注册成功"), REGISTRY_ERROR(504, "注册失败"),
    OAUTH_FAIL(501, "第三方认证失败"), OAUTH_SUCCESS(211, "第三方认证成功"), CODE_INVALID(504, "Code已过期"),
    DELETE_ARTICLE_FAIL(500, "删除文章失败"), UPDATE_USER_ERROR(500, "用户信息更新失败"), USERNAME_HAS_EXIST(500, "用户名已存在");


    /**
     * 状态码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
