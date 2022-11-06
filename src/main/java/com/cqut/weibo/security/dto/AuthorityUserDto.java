package com.cqut.weibo.security.dto;

import lombok.Data;

/**
 * @Description:权限分配读取用户数据dto
 * @Author:HL
 * @date:2021.05.10.13.47.34
 */
@Data
public class AuthorityUserDto {

    private Integer id;

    private String userName;

    private String userPhone;

    private String userAvatar;

    private String roleID;

}
