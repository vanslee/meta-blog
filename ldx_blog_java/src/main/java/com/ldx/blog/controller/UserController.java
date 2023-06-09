package com.ldx.blog.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.ldx.blog.pojo.User;
import com.ldx.blog.result.Result;
import com.ldx.blog.result.ResultCodeEnum;
import com.ldx.blog.service.impl.UserServiceImpl;
import com.ldx.blog.utils.IPUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author Uaena
 * @date 2023/5/21 12:01
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Resource
    private UserServiceImpl userService;

    @PostMapping("login")
    public Result<SaTokenInfo> doLoginApi(HttpServletRequest request, @RequestBody Map<String, String> loginForm) {
        String ip = IPUtil.ip(request);
        return userService.doLogin(loginForm, ip);
    }

    @PostMapping("registry")
    public Result<ResultCodeEnum> doRegistryApi(HttpServletRequest request, @RequestBody @Valid User user) {
        String ip = IPUtil.ip(request);
        return userService.doRegistry(user, ip);
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
