package com.cqut.weibo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeiboLike implements Serializable {
    private Integer weiboId;

    private Integer userId;

}