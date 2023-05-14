package org.weibo.hl.security.mapper;


import org.weibo.hl.security.api.pojo.req.UserInfoQueryReqDTO;
import org.weibo.hl.security.api.pojo.rsp.UserCommonInfoDTO;
import org.weibo.hl.security.pojo.User;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.security.mapper
 * @className: UserMapper
 * @description:
 * @author: hl
 * @createDate: 2023/5/10 21:27
 */

public interface UserMapper extends BaseMapper<User> {

    List<UserCommonInfoDTO> getUserInfo(UserInfoQueryReqDTO req);
}
