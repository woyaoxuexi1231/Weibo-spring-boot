package com.cqut.weibo.dao;

import com.cqut.weibo.dto.WeiboDto;
import com.cqut.weibo.dto.WeiboSubmitDto;
import com.cqut.weibo.pojo.Weibo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WeiboMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("deleted") boolean deleted);

    Integer insert(WeiboSubmitDto weiboSubmitDto);

    Weibo selectByPrimaryKey(Integer id);

    List<WeiboDto> selectAll(@Param("start") Integer start, @Param("limit") Integer limit, @Param("weiboText") String weiboText);

    int updateByPrimaryKey(Weibo record);

    List<WeiboDto> selectByUserId(Integer userId);

    List<WeiboDto> getWeiboFromFollowsAndMe(Integer uid);

    void updateCommentCountByWeiboId(Integer weiboId);

    void updateLikeCountByWeiboId(Integer weiboId);

    void submitWeiboImages(@Param("weiboId") Integer id, @Param("imageName") String imageName, @Param("imageURL") String imageURL);

    void insertCreateTime(@Param("id") Integer id, @Param("createTime") String createTime);

    void insertWeiboImageName(@Param("id") Integer id, @Param("imageName") String imageName);

    void submitWeiboVideo(@Param("id") Integer id, @Param("videoName") String videoName, @Param("videoURL") String videoURL);

    Integer getCount(@Param("weiboText") String weiboText);

    void lockWeiboById(@Param("id") Integer id, @Param("enabled") boolean enabled);

    void deleteWeiboById(Integer id);
}