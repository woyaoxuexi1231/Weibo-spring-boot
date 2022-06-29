package com.cqut.weibo.pojo;

import java.io.Serializable;

public class WeiboLike implements Serializable {
    private Integer weiboId;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(Integer weiboId) {
        this.weiboId = weiboId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}