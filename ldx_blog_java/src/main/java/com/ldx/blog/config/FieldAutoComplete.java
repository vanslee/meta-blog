package com.ldx.blog.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ldx.blog.pojo.Article;
import com.ldx.blog.pojo.ArticleComment;
import com.ldx.blog.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Uaena
 * @date 2023/5/26 21:39
 */
@Component
@Slf4j
public class FieldAutoComplete implements MetaObjectHandler {
    @Value("${website.config.cdn}")
    private String CDN;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert auto complete field ...");
        this.setFieldValByName("publishDate", System.currentTimeMillis() / 1000, metaObject);
        this.setFieldValByName("updateDate", System.currentTimeMillis() / 1000, metaObject);
        this.setFieldValByName("isDelete", false, metaObject);
        this.setFieldValByName("gender", false, metaObject);
        this.setFieldValByName("recentlyLanded", System.currentTimeMillis() / 1000, metaObject);
        this.setFieldValByName("createTime", System.currentTimeMillis() / 1000, metaObject);
        this.setFieldValByName("updateTime", System.currentTimeMillis() / 1000, metaObject);
        if (metaObject.getOriginalObject() instanceof User) {
            this.setFieldValByName("avatarImgUrl", (((User) metaObject.getOriginalObject()).getAvatarImgUrl().replace(CDN, "")), metaObject);
        }
        if (metaObject.getOriginalObject() instanceof ArticleComment) {
            this.setFieldValByName("userAvatar", ((ArticleComment) metaObject.getOriginalObject()).getUserAvatar().replace(CDN, ""), metaObject);
        }
        if (metaObject.getOriginalObject() instanceof Article) {
            this.setFieldValByName("imgUrl", ((Article) metaObject.getOriginalObject()).getImgUrl().replace(CDN, ""), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update auto complete field ...");
        this.setFieldValByName("updateDate", System.currentTimeMillis() / 1000, metaObject);
        this.setFieldValByName("recentlyLanded", System.currentTimeMillis() / 1000, metaObject);
        this.setFieldValByName("updateTime", System.currentTimeMillis() / 1000, metaObject);
    }
}
