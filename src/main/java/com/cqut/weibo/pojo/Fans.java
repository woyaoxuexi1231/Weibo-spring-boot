package com.cqut.weibo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Fans implements Serializable {
    private Integer id;

    private String userId;

    private String fansId;
}