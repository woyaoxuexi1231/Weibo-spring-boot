package org.weibo.hl.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weibo.hl.security.api.pojo.req.UserInfoQueryReqDTO;
import org.weibo.hl.security.api.pojo.rsp.UserCommonInfoDTO;
import org.weibo.hl.security.impl.UserService;

import java.util.List;


/**
 * @projectName: weibo
 * @package: org.weibo.hl.security.controller
 * @className: WeiboSearchProvider
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 20:27
 */

@RestController
public class ProviderForWeiboSearch {

    @Autowired
    UserService userService;

    @GetMapping("/getUserInfo")
    public List<UserCommonInfoDTO> getUserInfo(UserInfoQueryReqDTO req) {
        return userService.getUserInfo(req);
    }
}
