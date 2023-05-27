package com.ldx.blog.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.ldx.blog.pojo.User;
import com.ldx.blog.result.Result;
import com.ldx.blog.result.ResultCodeEnum;
import com.ldx.blog.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Uaena
 * @date 2023/5/21 12:01
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserServiceImpl userService;

    @PostMapping("login")
    public Result<Object> doLoginApi(HttpServletRequest request, @RequestBody Map<String, String> loginForm) {
        String ip = request.getRemoteAddr();
        return userService.doLogin(loginForm,ip);
    }

    @GetMapping("logout")
    public Result<ResultCodeEnum> logoutApi() {
        try {
            StpUtil.logout();
            return Result.success(ResultCodeEnum.LOGOUT_SUCCESS);
        } catch (RuntimeException e) {
            return Result.fail(ResultCodeEnum.FAIL);
        }
    }

    @GetMapping("info")
    public Result<User> userSafeInfoApi() {
        return userService.userSafeInfo();
    }
}
