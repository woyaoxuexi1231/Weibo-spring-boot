package com.cqut.weibo.dao;

import com.cqut.weibo.dto.VideoDto;
import com.cqut.weibo.pojo.WeiboVideo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WeiboVideoMapper {
    int insert(WeiboVideo record);

    List<WeiboVideo> selectAll();

    VideoDto selectByWeiboId(Integer id);
}