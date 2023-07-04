package com.ldx.blog;

import cn.dev33.satoken.secure.BCrypt;
import com.alibaba.fastjson.JSON;
import com.ldx.blog.pojo.Article;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Article article = new Article();
        ArrayList<String> tags = new ArrayList<>(2);
        tags.add("tag1");
        tags.add("tag2");
        ArrayList<String> categories = new ArrayList<>(2);
        categories.add("category1");
        categories.add("category2");
        article.setTagNames(tags);
        article.setCategoryNames(categories);
        String s = JSON.toJSONString(article);
        System.out.println(s);

    }
}
