package com.cqut.weibo.dao;

import com.cqut.weibo.security.dto.AuthorityUserDto;
import com.cqut.weibo.security.dto.UserDto;
import com.cqut.weibo.security.pojo.Role;
import com.cqut.weibo.security.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("enabled") boolean enabled);

    UserDto selectByPrimaryKey(Integer id);

    List<UserDto> selectAll(@Param("start") Integer start, @Param("limit") Integer limit, @Param("userName") String userName);

    Integer getCount(@Param("userName") String userName);

    void insertUser(User user);

    List<Integer> checkUserIsExist(@Param("userName") String userName, @Param("userPhone") String userPhone, @Param("uid") Integer uid);

    String getDefaultAvatar(Integer indexOfDefaultAvatar);

    int updateUser(User user);

    User loadUserByUsername(String userName);

    List<Role> getUserRolesByUid(Integer id);

    void updateUser(UserDto userDto, @Param("password") String password);

    void insertUserRole(Integer id);

    Integer updateUserById(@Param("id") Integer id, @Param("name") String name);

    Integer deleteUserById2(Integer id);

    List<AuthorityUserDto> authoritySelectAll(@Param("start") Integer start, @Param("limit") Integer limit, @Param("userName") String userName);

    void authorityChange(@Param("uid") Integer uid, @Param("rid") Integer rid);
}