package com.ldx.blog.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldx.blog.mapper.UserMapper;
import com.ldx.blog.pojo.User;
import com.ldx.blog.pojo.oath.gitee.GiteeUser;
import com.ldx.blog.pojo.oath.qq.QQUserInfo;
import com.ldx.blog.result.Result;
import com.ldx.blog.result.ResultCodeEnum;
import com.ldx.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    @Value("${redirect.url}")
    private String REDIRECT_URL;
    @Value("${redirect.error-page}")
    private String ERROR_PAGE;
    @Resource
    private UserMapper userMapper;

    public Result<SaTokenInfo> doLogin(Map<String, String> loginForm, String ip) {
        if (Objects.isNull(loginForm)) {
            log.error("登录用户信息为空");
            return Result.fail(ResultCodeEnum.LOGIN_PARAM_NULL);
        }
        String username = loginForm.get("username");
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username);
        User user = userMapper.selectOne(lqw);
        if (Objects.isNull(user)) {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
        String password = loginForm.get("password");
        if (BCrypt.checkpw(password, user.getPassword())) {
            StpUtil.login(user.getId());
            user.setIp(ip);
            user.setRecentlyLanded(System.currentTimeMillis() / 1000);
            userMapper.updateById(user);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            tokenInfo.setTag(username);
            return Result.success(ResultCodeEnum.LOGIN_SUCCESS, tokenInfo);
        } else {
            return Result.fail(ResultCodeEnum.LOGIN_ERROR);
        }
    }

    public Result<User> userSafeInfo() {
        String userId = (String) StpUtil.getLoginId();
        User user = userMapper.selectById(userId).setPassword("********").setPhone("131********");
        return Result.success(user);
    }

    public void oauthLogin(Object userInfo, String ip, HttpServletResponse response) throws IOException {
        // Gitee登录
        if (userInfo instanceof GiteeUser) {
            GiteeUser giteeUser = (GiteeUser) userInfo;
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getUnionId, giteeUser.getId());
            User user = userMapper.selectOne(lqw);
            if (Objects.isNull(user)) {
                long ts = System.currentTimeMillis() / 1000;
                String password = BCrypt.hashpw(String.valueOf(ts));
                // 如果用户不存在
                User newUser = new User().setIp(ip)
                        .setUnionId(giteeUser.getId())
                        .setUsername(giteeUser.getLogin())
                        .setPassword(password)
                        .setTrueName(giteeUser.getName())
                        .setAvatarImgUrl("default.jpg")
                        .setEmail(giteeUser.getEmail())
                        .setRecentlyLanded(ts);
                userMapper.insert(newUser);
                StpUtil.login(newUser.getId());
                response.sendRedirect(REDIRECT_URL + StpUtil.getTokenInfo().getTokenValue());
            } else {
                user.setIp(ip).setRecentlyLanded(System.currentTimeMillis() / 1000);
                userMapper.updateById(user);
                StpUtil.login(user.getId());
                response.sendRedirect(REDIRECT_URL + StpUtil.getTokenInfo().getTokenValue());
            }
            return;
        }
        // QQ登录
        if (userInfo instanceof QQUserInfo) {
            QQUserInfo qqUserInfo = (QQUserInfo) userInfo;
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getUnionId, qqUserInfo.getOpenid());
            User user = userMapper.selectOne(lqw);
            if (Objects.isNull(user)) {
                long ts = System.currentTimeMillis() / 1000;
                String password = BCrypt.hashpw(String.valueOf(ts));
                // 如果用户不存在
                User newUser = new User().setIp(ip)
                        .setUnionId(qqUserInfo.getOpenid())
                        .setUsername(qqUserInfo.getNickname())
                        .setPassword(password)
                        .setTrueName(qqUserInfo.getNickname())
                        .setAvatarImgUrl("default.jpg")
                        .setRecentlyLanded(ts);
                userMapper.insert(newUser);
                StpUtil.login(newUser.getId());
                response.sendRedirect(REDIRECT_URL + StpUtil.getTokenInfo().getTokenValue());
            } else {
                user.setIp(ip).setRecentlyLanded(System.currentTimeMillis() / 1000);
                userMapper.updateById(user);
                StpUtil.login(user.getId());
                response.sendRedirect(REDIRECT_URL + StpUtil.getTokenInfo().getTokenValue());
            }
            return;
        }
        log.error("第三方登录失败");
        response.sendRedirect(ERROR_PAGE);
    }

    public Result<ResultCodeEnum> doRegistry(User params, String ip) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, params.getUsername());
        User user = userMapper.selectOne(lqw);
        if (!Objects.isNull(user)) {
            return Result.fail(ResultCodeEnum.USER_HAS_EXIST);
        }
        String hashpw = BCrypt.hashpw(params.getPassword());
        User newUser = new User()
                .setIp(ip)
                .setPassword(hashpw)
                .setUsername(params.getUsername())
                .setAvatarImgUrl("blog-litubao/th.jpg");
        int effectRows = userMapper.insert(newUser);
        if (effectRows > 0) {
            return Result.success(ResultCodeEnum.REGISTRY_SUCCESS);
        } else {
            return Result.fail(ResultCodeEnum.REGISTRY_ERROR);
        }
    }
}




