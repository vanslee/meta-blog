package com.ldx.blog;

import cn.dev33.satoken.secure.BCrypt;
import com.alibaba.fastjson.JSONObject;
import com.ldx.blog.pojo.Article;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class LdxBlogJavaApplicationTests {

    @Test
    void contextLoads() {
        String hashpw = BCrypt.hashpw("zhou2001.");
        System.out.println(hashpw);
    }

    @Test
    void test(){
        String hashpw = BCrypt.hashpw("zhou2001.");
        System.out.println(hashpw);
        boolean checkpw = BCrypt.checkpw("zhou2001.", hashpw);
        System.out.println(checkpw);
    }
    @Test
    void parseStr(){
 String        str = "{\n" +
         "    \"articleContent\": \"sadas\",\n" +
         "    \"articleTitle\": \"测试\",\n" +
         "    \"categories\": [1],\n" +
         "    \"tags\": [2]\n" +
         "}";
        JSONObject article = JSONObject.parseObject(str);
        Article article1 = article.toJavaObject(Article.class);

        Object categories = article.get("categories");;
        System.out.println(article);
        System.out.println(article1);

    }
}
