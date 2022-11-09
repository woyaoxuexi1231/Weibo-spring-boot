package com.cqut.weibo.service;

import com.cqut.weibo.dto.Result;
import com.cqut.weibo.dto.WeiboSubmitDto;
import com.cqut.weibo.pojo.Comment;
import com.cqut.weibo.pojo.WeiboLike;
import com.cqut.weibo.security.pojo.User;

import java.text.ParseException;
import java.util.Map;


public interface UsersService {

    Result getUserById(Integer id);

    Result submitWeibo(WeiboSubmitDto weiboSubmitDto);

    Result getWeiboByUserId(Integer userId);

    Result getWeiboCountByUserId(Integer userId);

    Result getFollowCountById(Integer userId);

    Result getFollowerCountById(Integer userId);

    Result getUsersByLikeName(String likeName, Integer uid);

    Result addFollow(Integer followId, Integer uid);

    Result getWeiboFromFollowsAndMe(Integer uid);

    Result getFollowsByUid(Integer uid);

    Result getFollowersByUid(Integer uid);

    Result deleteFollow(Integer id);

    Result submitCommentInput(Comment comment);

    Result getCommentListByWeiboId(Integer weiboId);

    Result submitWeiboLike(WeiboLike weiboLike);

    Result deleteWeiboLike(WeiboLike weiboLike);

    Result deleteWeiboById(Integer id);

    Result updateUser(User user);

    String addOrderInformation(Map map) throws ParseException;

    boolean checkIsVip(Integer userID);

    Result checkUserIsExist(String userName, String userPhone, Integer uid);

    Result getWeiboBySearchId(Integer userId, Integer searchId);
}
