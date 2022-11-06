package com.cqut.weibo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class WeiboDto {
    private Integer id;

    private String weiboText;

    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date weiboTime;

    private String userAvatar;

    private String userName;

    private boolean commentMenuState = false;

    private List<CommentDto> commentList;

    private Integer repostCount;

    private Integer commentCount;

    private Integer likeCount;

    //标记当前用户是否点赞
    //默认为没有点赞
    private boolean likeState = false;

    private List<ImageListDto> imageList;

    private VideoDto video;

    private boolean enabled;

    private boolean deleted;

}
