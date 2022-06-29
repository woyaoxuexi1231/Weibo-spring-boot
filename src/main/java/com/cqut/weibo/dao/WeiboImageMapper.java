package com.cqut.weibo.dao;

import com.cqut.weibo.dto.ImageListDto;
import com.cqut.weibo.pojo.WeiboImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WeiboImageMapper {
    int insert(WeiboImage record);

    List<WeiboImage> selectAll();

    List<ImageListDto> selectByWeiboId(Integer id);
}