package com.cqut.weibo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<E> implements Serializable {
    private Integer code = 200;
    private String message = "成功";
    private Integer count;
    private E data;
}
