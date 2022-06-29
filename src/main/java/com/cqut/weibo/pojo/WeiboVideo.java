package com.cqut.weibo.pojo;

import java.io.Serializable;

public class WeiboVideo implements Serializable {
    private Integer weiboId;

    private String videoUrl;

    private String videoName;

    private static final long serialVersionUID = 1L;

    public Integer getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(Integer weiboId) {
        this.weiboId = weiboId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }
}