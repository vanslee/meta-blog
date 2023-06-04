package com.ldx.blog.utils;

import com.ldx.blog.pojo.oath.GiteeAccessToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Uaena
 * @date 2023/6/4 0:43
 */
@SpringBootTest
@ActiveProfiles("test")
class HttpUtilTest {
    @Value("${oauth.gitee.client_id}")
    private String GITEE_CLIENT_ID;
    @Value("${oauth.gitee.client_secret}")
    private String GITEE_CLIENT_SECRET;
    @Value("${oauth.gitee.redirect_uri}")
    private String GITEE_REDIRECT_URI;
    @Test
    void getAccessToken() {
        String code = "7eaa28ca543f780cb475215c93d53a7d8760e7de05aba01088c2cbbb27e43996";
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code&code="+code+"&client_id="+GITEE_CLIENT_ID+"&redirect_uri="+GITEE_REDIRECT_URI+"&client_secret="+GITEE_CLIENT_SECRET;
        GiteeAccessToken accessToken = HttpUtil.getAccessToken(url, GiteeAccessToken.class);
        System.out.println(accessToken);
    }
}