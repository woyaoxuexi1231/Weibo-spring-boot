package com.cqut.weibo.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {
    private Integer id;

    private String userName;

    private String userPhone;

    private String userSex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date userBirth;

    private String userDesc;

    private String userAvatar;

    private Integer followCount;

    private Integer followerCount;

    private Integer weiboCount;

    private Boolean enabled;

    private Boolean locked;

    private boolean follow = false;

}
