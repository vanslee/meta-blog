package com.ldx.blog.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.UserMapper;
import com.ldx.blog.pojo.User;
import com.ldx.blog.result.Result;
import com.ldx.blog.result.ResultCodeEnum;
import com.ldx.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author ldx
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-05-15 23:17:28
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

    public Result<Object> doLogin(Map<String, String> loginForm) {
        if (Objects.isNull(loginForm)) {
            log.error("登录用户信息为空");
            return Result.fail(ResultCodeEnum.LOGIN_PARAM_NULL);
        }
        String username = loginForm.get("username");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username).eq(User::getIsDelete, 0);
        User user = userMapper.selectOne(lqw);
        if (Objects.isNull(user)) {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        String password = loginForm.get("password");
        if (BCrypt.checkpw(password, user.getPassword())) {
            StpUtil.login(user.getId());
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            tokenInfo.setTag(username);
            return Result.success(ResultCodeEnum.LOGIN_SUCCESS, tokenInfo);
        } else {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
    }

    public Result<User> userSafeInfo(String userId) {
        User user = userMapper.selectById(userId);
        return Result.success(user);
    }
}




