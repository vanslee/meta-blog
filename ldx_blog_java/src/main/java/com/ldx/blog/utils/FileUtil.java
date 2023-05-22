package com.ldx.blog.utils;

import com.ldx.blog.result.OssClientConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

/**
 * @author Uaena
 * @date 2023/5/20 13:10
 */
@Slf4j
public class FileUtil {
    /**
     * 上传文件到七牛云OSS
     *
     * @param file 文件流
     * @return 返回文件URL
     */
    public String uploadFile(File file, String subCatalog) {
        log.info("FileUtil uploadFile filePath:[{}],fileName:[{}]", file.getPath(), file.getName());
        //上传至七牛云OSS
        String fileName = QiNiuYunOssUtil.uploadFile2OSS(file);
        String picUrl = "https://" + OssClientConstants.ENDPOINT + "/" + fileName;

        //删除临时生成的文件
        File deleteFile = new File(file.toURI());
        deleteFile.delete();

        return picUrl;

    }

    /**
     * base64字符转换成file
     *
     * @param destPath 保存的文件路径
     * @param base64   图片字符串
     * @param fileName 保存的文件名
     * @return file
     */
    public File base64ToFile(String destPath, String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 将file转换成base64字符串
     *
     * @param path
     * @return
     */
    public String fileToBase64(String path) {
        String base64 = null;
        InputStream in = null;
        try {
            File file = new File(path);
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    /**
     * MultipartFile类型文件转File
     *
     * @return File类型文件
     */
    public static File multipartFileToFile(MultipartFile multipartFile, String filePath, String fileName) {
        File f = null;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        if (StringUtil.isEmpty(multipartFile.getName()) || multipartFile.getSize() <= 0) {
           log.error("用户上传了空文件:{}",System.currentTimeMillis());
        } else {
            try {
                InputStream ins = multipartFile.getInputStream();
                f = new File(filePath + fileName);
                OutputStream os = new FileOutputStream(f);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

}
