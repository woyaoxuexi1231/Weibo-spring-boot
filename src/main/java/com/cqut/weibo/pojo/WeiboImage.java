package com.cqut.weibo.pojo;

import java.io.Serializable;

public class WeiboImage implements Serializable {
    private Integer weiboId;

    private String imageUrl;

    private String imageName;

    private static final long serialVersionUID = 1L;

    public Integer getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(Integer weiboId) {
        this.weiboId = weiboId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName == null ? null : imageName.trim();
    }
}