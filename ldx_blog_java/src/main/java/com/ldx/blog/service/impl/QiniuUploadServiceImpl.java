package com.ldx.blog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ldx.blog.result.Result;
import com.ldx.blog.result.ResultCodeEnum;
import com.ldx.blog.service.QiniuUploadService;
import com.ldx.blog.utils.FileUtil;
import com.ldx.blog.utils.QiNiuYunOssUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Uaena
 * @date 2023/5/20 12:51
 */
@Service
public class QiniuUploadServiceImpl implements QiniuUploadService {
    private static final int CORE_POOL_SIZE = 8;
    private static final int MAX_POOL_SIZE = 16;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;
    private static final ArrayBlockingQueue ARRAY_BLOCKING_QUEUE = new ArrayBlockingQueue(QUEUE_CAPACITY);

    @Value("${website.config.cdn}")
    public String qiniuyun;

    @Override
    public Result<Object> saveFileToQiNiu(MultipartFile file) {
        if (Objects.isNull(file)) {
            return Result.fail(ResultCodeEnum.UPLOAD_FILE_ERROR);
        }
        // 获取路径
        File tempFile = new File("");
        String pathInProject = null;
        try {
            pathInProject = tempFile.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 文件名
        String originalFilename = file.getOriginalFilename();
        // 文件后缀名
        int i = originalFilename.lastIndexOf('.');
        String suffixName = "";
        if (i != -1) {
            //如果没有后缀则为""
            suffixName = originalFilename.substring(i);
        }
        File uploadFile = FileUtil.multipartFileToFile(file, pathInProject + "\\files\\", originalFilename);
        // 文件全路径名
        String urlInQiniu = qiniuyun + QiNiuYunOssUtil.uploadFile2OSS(uploadFile);
        Map<String, String> returnFileName = new HashMap<>(3);
        returnFileName.put("name", originalFilename);
        returnFileName.put("suffix", suffixName);
        returnFileName.put("url", urlInQiniu);
        if (Objects.isNull(urlInQiniu)) {
            return Result.fail(ResultCodeEnum.UPLOAD_FILE_ERROR);
        }
        return Result.success(ResultCodeEnum.UPLOAD_FILE_SUCCESS,returnFileName);
    }

    @Override
    public Result<Object> saveMultipartFileToQiNiu(MultipartFile[] files) {
        JSONArray jsonArray = new JSONArray();
        for (MultipartFile file : files) {
                Result<Object> objectResult = saveFileToQiNiu(file);
                jsonArray.add(objectResult.getData());

        }
        if (jsonArray.size() < 1) {
            return Result.fail(ResultCodeEnum.UPLOAD_FILE_ERROR);
        }
        return Result.success(ResultCodeEnum.UPLOAD_FILE_SUCCESS,jsonArray);
    }
}

