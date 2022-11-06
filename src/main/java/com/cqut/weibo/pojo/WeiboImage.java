package com.cqut.weibo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeiboImage implements Serializable {
    private Integer weiboId;

    private String imageUrl;

    private String imageName;
}