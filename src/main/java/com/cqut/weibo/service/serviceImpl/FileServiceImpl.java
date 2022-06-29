package com.cqut.weibo.service.serviceImpl;


import com.cqut.weibo.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;


public class FileServiceImpl {

    @Autowired
    private FileDao fileDao;


    public void saveImage(String url) {
        this.fileDao.saveImage(url);
    }
}

