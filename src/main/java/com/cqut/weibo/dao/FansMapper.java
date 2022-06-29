package com.cqut.weibo.dao;

import com.cqut.weibo.pojo.Fans;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FansMapper {
    int deleteByPrimaryKey(Integer id);

    void insert(@Param("followId") Integer followId, @Param("uid") Integer uid);

    Fans selectByPrimaryKey(Integer id);

    List<Fans> selectAll();

    int updateByPrimaryKey(Integer id);

    List<Integer> selectFollowsIdsByUid(Integer uid);

    Integer isFollow(@Param("followId") Integer followId, @Param("uid") Integer uid);

    void deleteFollow(Integer id);
}