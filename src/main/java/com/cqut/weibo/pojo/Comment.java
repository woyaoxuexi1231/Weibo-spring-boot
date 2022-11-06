package com.cqut.weibo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    private Integer id;

    private Integer weiboId;

    private String commentText;

    private Date commentCreateTime;

    private Integer commentUserId;
}