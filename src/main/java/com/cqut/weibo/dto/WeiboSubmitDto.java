package com.cqut.weibo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class WeiboSubmitDto {

    private Integer id;

    private String weiboText;

    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date weiboTime;

    private String[] imageNameList;

    private String[] imageURLList;

    private String videoName;

    private String videoURL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeiboText() {
        return weiboText;
    }

    public void setWeiboText(String weiboText) {
        this.weiboText = weiboText;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getWeiboTime() {
        return weiboTime;
    }

    public void setWeiboTime(Date weiboTime) {
        this.weiboTime = weiboTime;
    }

    public String[] getImageNameList() {
        return imageNameList;
    }

    public void setImageNameList(String[] imageNameList) {
        this.imageNameList = imageNameList;
    }

    public String[] getImageURLList() {
        return imageURLList;
    }

    public void setImageURLList(String[] imageURLList) {
        this.imageURLList = imageURLList;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
