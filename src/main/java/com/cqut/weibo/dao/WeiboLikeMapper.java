package com.cqut.weibo.dao;

import com.cqut.weibo.pojo.WeiboLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WeiboLikeMapper {
    int deleteByPrimaryKey(Integer weiboId);

    int insert(WeiboLike record);

    WeiboLike selectByPrimaryKey(Integer weiboId);

    List<WeiboLike> selectAll();

    int updateByPrimaryKey(WeiboLike record);

    WeiboLike selectWeiboLikeState(@Param("weiboId") Integer id, @Param("userId") Integer uid);

    void delete(@Param("weiboLike") WeiboLike weiboLike);
}