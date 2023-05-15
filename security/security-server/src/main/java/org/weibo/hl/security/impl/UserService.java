package org.weibo.hl.security.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.weibo.hl.security.api.pojo.req.UserInfoQueryReqDTO;
import org.weibo.hl.security.api.pojo.rsp.UserCommonInfoDTO;
import org.weibo.hl.security.mapper.RoleMapper;
import org.weibo.hl.security.mapper.UserMapper;
import org.weibo.hl.security.pojo.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.security.impl
 * @className: UserService
 * @description:
 * @author: hl
 * @createDate: 2023/5/10 21:29
 */

@Service
public class UserService implements UserDetailsService {

    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(username);
        user = userMapper.selectOne(user);
        user.setAuthorities(roleMapper.getRoleByUserId(user.getId()));
        return user;
    }

    /**
     * 提供给外部的获取用户信息的 API 接口
     *
     * @param req
     * @return
     */
    public List<UserCommonInfoDTO> getUserInfo(UserInfoQueryReqDTO req) {
        return userMapper.getUserInfo(req);
    }
}
