package com.cqut.weibo.controller;


import com.cqut.weibo.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping(value = "file")
@CrossOrigin(allowCredentials = "true")
public class FileUpload {

    @Autowired
    private AppConfig config;


    /**
     * 图片上传
     *
     * @param httpReq
     * @param MulReq
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public List<String> upload(HttpServletRequest httpReq, MultipartHttpServletRequest MulReq) {
        // String ctxPath = httpReq.getSession().getServletContext().getRealPath("/");
        List<String> result = new ArrayList<>();
        List<MultipartFile> files = MulReq.getFiles("file");
        if (files.size() > 0) {
            for (MultipartFile file : files) {
                //                System.out.println(multipartFile);
                result.add(handleFileUpload(file, MulReq));
            }
        }
        return result;
    }


    /**
     * 文件处理和保存
     *
     * @param file
     * @param request
     * @return
     */
    private String handleFileUpload(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String saveFileName = getFileName(file);
            System.out.println(file.getName());
            File saveFile = new File(config.getUploadFolder() + saveFileName);
//            System.out.println(saveFile.getAbsolutePath());
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(saveFile.toPath()));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return config.getPreviewPath() + saveFile.getName();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败,";
            }
        } else {
            return "上传失败，因为文件为空.";
        }
    }

    /**
     * 生成文件名
     *
     * @param file
     * @return
     */
    private String getFileName(MultipartFile file) {
        String name = file.getOriginalFilename();
        Date date = new Date();
        assert name != null;
        return (date.getTime() + "" + getRandomString(20)) + name.substring(name.indexOf("."));
    }

    /**
     * 生成一个随机字符串
     *
     * @param length
     * @return
     */
    private String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
