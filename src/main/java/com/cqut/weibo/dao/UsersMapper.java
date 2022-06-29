package com.cqut.weibo.dao;

import com.cqut.weibo.dto.FollowDto;
import com.cqut.weibo.dto.FollowerDto;
import com.cqut.weibo.security.dto.UserDto;
import com.cqut.weibo.security.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface UsersMapper {
    UserDto selectByPrimaryKey(Integer id);

    Integer getWeiboCountByUserId(Integer userId);

    Integer getFollowCountById(Integer userId);

    Integer getFollowerCountById(Integer userId);

    List<UserDto> getUsersByLikeName(@Param("likeName") String likeName, @Param("uid") Integer uid);

    List<Integer> getFollowList(Integer uid);

    List<FollowDto> getFollowsByUid(Integer uid);

    List<FollowerDto> getFollowersByUid(Integer uid);

    int updateUser(User user);

    void addOrderInformation(@Param("orderID") String orderID, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("userID") Integer userID, @Param("amount") Integer amount, @Param("orderName") String orderName);

    Integer checkIsVip(Integer userID);

    String getEndTime(Integer userID);

    void updateVipInformation(@Param("endTimeBefore") Date endTimeBefore, @Param("userID") Integer userID);

    Integer checkIsVipBefore(Integer userID);

    void addVipInformation(@Param("endTime") Date endTime, @Param("userID") Integer userID);
}
