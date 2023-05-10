package org.weibo.hl.security.mapper;

import org.apache.ibatis.annotations.Param;
import org.weibo.hl.security.pojo.Role;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.security.mapper
 * @className: RoleMapper
 * @description:
 * @author: h1123
 * @createDate: 2023/5/10 21:27
 */

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户 ID 获取用户角色信息
     *
     * @param id
     * @return
     */
    List<Role> getRoleByUserId(@Param("userId") Long id);
}
