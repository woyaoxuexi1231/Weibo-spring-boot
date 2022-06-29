package com.cqut.weibo.dao;

import com.cqut.weibo.dto.CommentDto;
import com.cqut.weibo.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("comment") Comment comment, @Param("createTime") String createTime);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    List<CommentDto> selectByWeiboId(Integer weiboId);
}