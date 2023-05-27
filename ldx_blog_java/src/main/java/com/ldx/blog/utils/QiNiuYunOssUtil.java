package com.ldx.blog.utils;

/**
 * @author Uaena
 * @date 2023/5/20 13:06
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ldx.blog.result.OssClientConstants;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @description: 七牛云对象存储OSS工具类
 * @author: liuchuanfeng
 * @time: 2020/12/14 17:00
 */
@Slf4j
public class QiNiuYunOssUtil {

    private static String ENDPOINT;

    private static String ACCESS_KEY_ID;

    private static String ACCESS_KEY_SECRET;

    private static String BACKET_NAME;

    private static String FOLDER;

    //初始化属性
    static {
        ENDPOINT = OssClientConstants.ENDPOINT;
        // 密钥
        ACCESS_KEY_ID = OssClientConstants.ACCESS_KEY_ID;
        // 密钥
        ACCESS_KEY_SECRET = OssClientConstants.ACCESS_KEY_SECRET;
        // 存储空名字
        BACKET_NAME = OssClientConstants.BACKET_NAME;
        // 文件上传路径
        FOLDER = OssClientConstants.FOLDER;
    }

    /**
     * 普通文件上传
     *
     * @param file 需要上传的文件
     * @return
     */


    /**
     * 上传图片至OSS
     *
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     *             //     * @param subCatalog 模拟文件夹名 如"img/"
     * @return String 文件名
     */
    public static String uploadFile2OSS(File file) {

        String resultStr = null;
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadongZheJiang2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "img" + "/" + file.getName();
        log.info("qiniuyun key: [{}]", key);
        try {
            try {
                Auth auth = Auth.create(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
                String upToken = auth.uploadToken(BACKET_NAME, key);
                log.info("qiniuyun upToken:[{}] filePath: [{}] , fileSize: [{}]MB", upToken, file.getPath(), file.length() >> 20);
                Response response = uploadManager.put(file, key, upToken);
                //解析上传成功的结果(将字符串解析成Java对象)
                String bodyString = response.bodyString();
                Object parse = JSON.parse(bodyString);
                String s = parse.toString();
                DefaultPutRet putRet = JSONObject.parseObject(s, DefaultPutRet.class);
                log.info("qiniuyun putRet.key: [{}]", putRet.key);
                resultStr = putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
            }
        } catch (Exception ex) {
            log.error("qiniuyunoss uploadFile2OSS" + ex);
        }
        return resultStr;
    }

}
