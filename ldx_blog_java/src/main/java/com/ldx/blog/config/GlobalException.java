package com.ldx.blog.config;

import cn.dev33.satoken.exception.NotLoginException;
import com.ldx.blog.result.Result;
import com.ldx.blog.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Uaena
 * @date 2023/5/26 21:34
 */
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e){
        e.printStackTrace();
        return Result.fail(ResultCodeEnum.FAIL);
    }
    @ExceptionHandler(NotLoginException.class)
    public Result<String> handleTokenInvalidException(Exception e){
        e.printStackTrace();
        return Result.fail(ResultCodeEnum.NOT_LOGIN);
    }

}
