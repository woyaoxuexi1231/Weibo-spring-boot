package com.cqut.weibo.security.controller;

import com.cqut.weibo.dto.Result;
import com.cqut.weibo.security.pojo.User;
import com.cqut.weibo.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CacheConfig(cacheNames = {"memberService"})
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户管理获取所有用户
     *
     * @param page
     * @param limit
     * @param userName
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:12 2021/5/10
     */
    @RequestMapping("/getAllUser")
    //@Cacheable
    public Result getAllUser(Integer page, Integer limit, String userName) {
        return userService.getAllUser(page, limit, userName);
    }

    /**
     * 微博管理获取所有微博
     *
     * @param page
     * @param limit
     * @param weiboText
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:13 2021/5/10
     */
    @RequestMapping("getAllWeibo")
    public Result getAllWeibo(Integer page, Integer limit, String weiboText) {
        return userService.getAllWeibo(page, limit, weiboText);
    }

    /**
     * 用户管理新增用户
     *
     * @param user
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:13 2021/5/10
     */
    @RequestMapping("/insertUser")
    public Result insertUser(User user) {
        return userService.insertUser(user);
    }

    /**
     * 查询用户是否已经存在
     *
     * @param userName
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:14 2021/5/10
     */
    @RequestMapping("/checkUserIsExist")
    public Result checkUserIsExist(String userName, String userPhone, Integer uid) {
        return userService.checkUserIsExist(userName, userPhone, uid);
    }

    /**
     * 随机获取系统默认头像
     *
     * @param
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:15 2021/5/10
     */
    @RequestMapping("/getDefaultAvatar")
    public Result getDefaultAvatar() {
        return userService.getDefaultAvatar();
    }

    /**
     * 用过用户id查询用户
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:16 2021/5/10
     */
    @RequestMapping("/getUserById")
    public Result getUserById(Integer id) {
        return userService.getUserById(id);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public Result updateUser(User user) {
        return userService.updateUser(user);
    }

    /**
     * 通过用户id修改用户名
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/updateUserById")
    public Result updateUserById(Integer id, String name) {
        return userService.updateUserById(id, name);
    }

    /**
     * 通过用户id删除用户
     *
     * @param id
     * @param enabled
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:17 2021/5/10
     */
    @RequestMapping("/deleteUserById")
    public Result deleteUserById(Integer id, boolean enabled) {
        return userService.deleteUserById(id, enabled);
    }

    /**
     * 通过微博id号删除微博
     *
     * @param id
     * @param deleted
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:17 2021/5/10
     */
    @RequestMapping("/deleteWeiboById")
    public Result deleteWeiboById(Integer id, boolean deleted) {
        return userService.deleteWeiboById(id, deleted);
    }

    /**
     * 通过微博id号锁定微博
     *
     * @param id
     * @param enabled
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:18 2021/5/10
     */
    @RequestMapping("/lockWeiboById")
    public Result lockWeiboById(Integer id, boolean enabled) {
        return userService.lockWeiboById(id, enabled);
    }

    /**
     * 权限管理获取所有用户信息
     *
     * @param page
     * @param limit
     * @param userName
     * @return
     * @author HL.
     * @Date 13:38 2021/5/10
     */
    @RequestMapping("/authorityGetAllUser")
    public Result authorityGetAllUser(Integer page, Integer limit, String userName) {
        return userService.authorityGetAllUser(page, limit, userName);
    }


    /**
     * 通过用户id修改用户的权限
     *
     * @param uid
     * @param rid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 15:00 2021/5/10
     */
    @RequestMapping("/authorityChange")
    public Result authorityChange(Integer uid, Integer rid) {
        return userService.authorityChange(uid, rid);
    }
}
