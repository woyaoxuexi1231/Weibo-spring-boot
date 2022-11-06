package com.cqut.weibo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
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

}
