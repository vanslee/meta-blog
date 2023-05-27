package com.ldx.blog.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Uaena
 * @date 2023/5/27 13:11
 */
@Component
public class RedisKeyGenerator implements KeyGenerator {
    /**
     *
     * @param target the target instance
     * @param method the method being called
     * @param params the method parameters (with any var-args expanded)
     * @return
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
      
    }
}
