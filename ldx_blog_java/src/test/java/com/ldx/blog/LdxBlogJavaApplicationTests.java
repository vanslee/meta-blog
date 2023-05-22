package com.ldx.blog;

import cn.dev33.satoken.secure.BCrypt;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class LdxBlogJavaApplicationTests {

    @Test
    void contextLoads() {
        String hashpw = BCrypt.hashpw("zhou2001.");
        System.out.println(hashpw);
    }

}
