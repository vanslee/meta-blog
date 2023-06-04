package com.ldx.blog.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @author ldx
 */
@Slf4j
public class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();

    private HttpUtil() {}

    private static class SingletonHolder {
        private static final OkHttpClient INSTANCE = new OkHttpClient();
    }

    public static OkHttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public static  <T> T getAccessToken(String url, Class<T> type){
        log.info("========请求路径:{}",url);
        log.info("========转换结果:{}",type);
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), "");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == HttpStatus.OK.value()){
               return JSON.parseObject(response.body().bytes(), type);
            }else {
                log.error(response.body().bytes().toString());
            }

        } catch (IOException e) {
            log.error("获取AccessToken出现错误:{}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}