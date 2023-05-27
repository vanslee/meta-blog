package com.ldx.blog.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author Uaena
 * @date 2023/5/26 21:39
 */
@Component
@Slf4j
public class FieldAutoComplete implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
      log.info("start insert auto complete field ...");
      this.setFieldValByName("publishDate",System.currentTimeMillis()/1000,metaObject);
      this.setFieldValByName("updateDate",System.currentTimeMillis()/1000,metaObject);
      this.setFieldValByName("isDelete",0,metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update auto complete field ...");
        this.setFieldValByName("updateDate",System.currentTimeMillis()/1000,metaObject);
    }
}