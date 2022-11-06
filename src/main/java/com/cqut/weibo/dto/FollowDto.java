package com.cqut.weibo.dto;

import lombok.Data;

@Data
public class FollowDto {
    private Integer id;
    private Integer fid;
    private String userAvatar;
    private String userName;
    private String userDesc;
    private boolean follow;
}
