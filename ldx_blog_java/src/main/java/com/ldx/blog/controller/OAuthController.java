package com.ldx.blog.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.ldx.blog.pojo.oath.GiteeAccessToken;
import com.ldx.blog.pojo.oath.gitee.GiteeUser;
import com.ldx.blog.result.Result;
import com.ldx.blog.service.impl.UserServiceImpl;
import com.ldx.blog.utils.HttpUtil;
import com.ldx.blog.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Uaena
 * @date 2023/6/3 19:19
 */
@Profile("!test")
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @Resource
    private UserServiceImpl userService;
    @Value("${oauth.gitee.client_id}")
    private String GITEE_CLIENT_ID;
    @Value("${oauth.gitee.client_secret}")
    private String GITEE_CLIENT_SECRET;
    @Value("${oauth.gitee.redirect_uri}")
    private String GITEE_REDIRECT_URI;
    private final String GITEE_TOKEN_URI = "https://gitee.com/oauth/token?grant_type=authorization_code&";

    @GetMapping("/qq/login")
    public Result<String> qqLogin() {
        return Result.success("success");
    }

    @GetMapping("/wechat/login")
    public Result<String> wechatLogin() {
        return Result.success("success");
    }

    @GetMapping("/gitee/login")
    public Result<SaTokenInfo> giteeLogin(HttpServletRequest request, @RequestParam String code) {
        String ip = request.getRemoteAddr();
        // 获取Token
        String access_token = GITEE_TOKEN_URI+code+"&client_id="+GITEE_CLIENT_ID+"&redirect_uri="+GITEE_REDIRECT_URI+"&client_secret="+GITEE_CLIENT_SECRET;
        GiteeAccessToken accessToken = HttpUtil.getAccessToken(access_token, GiteeAccessToken.class);
        String user_info = "https://gitee.com/api/v5/user?access_token?"+accessToken.getAccessToken();
        String email_info = "https://gitee.com/api/v5/emails?access_token?"+accessToken.getAccessToken();
        GiteeUser giteeUser = RestTemplateUtils.get(user_info, GiteeUser.class);
        giteeUser.setEmail(RestTemplateUtils.get(email_info, GiteeUser.class).getEmail());
        return userService.oauthLogin(giteeUser,ip);

    }
//    @GetMapping("/callback")
//    public Result<String> callback(Oa){
//
//    }
}
