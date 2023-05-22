package com.ldx.blog.controller;

import com.ldx.blog.pojo.User;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public Result<Object> doLoginApi(@RequestBody Map<String,String> loginForm){
        return userService.doLogin(loginForm);
    }
    @GetMapping("info/{id}")
    public Result<User> userSafeInfoApi(@PathVariable("id") String id){
        return userService.userSafeInfo(id);
    }
}
