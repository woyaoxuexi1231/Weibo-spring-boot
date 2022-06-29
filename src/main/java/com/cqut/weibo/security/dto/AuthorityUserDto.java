package com.cqut.weibo.security.dto;

/**
 * @Description:权限分配读取用户数据dto
 * @Author:HL
 * @date:2021.05.10.13.47.34
 */
public class AuthorityUserDto {

    private Integer id;

    private String userName;

    private String userPhone;

    private String userAvatar;

    private String roleID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
}
