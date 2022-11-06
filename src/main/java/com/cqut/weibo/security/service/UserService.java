package com.cqut.weibo.security.service;

import com.cqut.weibo.dto.Result;
import com.cqut.weibo.security.pojo.User;

public interface UserService {

    Result getAllUser(Integer page, Integer limit, String userName);

    Result insertUser(User user);

    Result checkUserIsExist(String userName, String userPhone, Integer uid);

    Result getDefaultAvatar();

    Result getUserById(Integer id);

    Result updateUser(User user);

    Result deleteUserById(Integer id, boolean enabled);

    Result getAllWeibo(Integer page, Integer limit, String weiboText);

    Result deleteWeiboById(Integer id, boolean deleted);

    Result lockWeiboById(Integer id, boolean enabled);

    Result updateUserById(Integer id, String name);

    Result deleteUserById2(Integer id);

    Result authorityGetAllUser(Integer page, Integer limit, String userName);

    Result authorityChange(Integer uid, Integer rid);
}
