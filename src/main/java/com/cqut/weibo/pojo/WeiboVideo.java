package com.cqut.weibo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeiboVideo implements Serializable {
    private Integer weiboId;

    private String videoUrl;

    private String videoName;

}