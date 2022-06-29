package com.cqut.weibo.controller;

import com.cqut.weibo.dto.Result;
import com.cqut.weibo.dto.WeiboSubmitDto;
import com.cqut.weibo.pojo.Comment;
import com.cqut.weibo.pojo.HotWord;
import com.cqut.weibo.pojo.WeiboLike;
import com.cqut.weibo.security.pojo.User;
import com.cqut.weibo.security.service.UserService;
import com.cqut.weibo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@RestController
@RequestMapping("/user")
public class UsersController {

    private static final Logger logger = Logger.getLogger(String.valueOf(UsersController.class));


    /**
     * 普通用户用户服务层
     */
    @Autowired
    private UsersService usersService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 管理员用户服务层
     */
    @Autowired
    private UserService userService;

    /**
     * 通过用户id查询用户信息
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:04 2021/5/13
     */
    @RequestMapping("/getUserById")
    public Result getUserById(Integer id) {
        return usersService.getUserById(id);
    }

    /**
     * 发表微博
     *
     * @param weiboSubmitDto
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:05 2021/5/13
     */
    @RequestMapping("/submitWeibo")
    public Result submitWeibo(WeiboSubmitDto weiboSubmitDto) {
        return usersService.submitWeibo(weiboSubmitDto);
    }

    /**
     * 通过用户id查询微博（当前id的所有微博）
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:05 2021/5/13
     */
    @RequestMapping("/getWeiboByUserId")
    public Result getWeiboByUserId(Integer userId) {
        return usersService.getWeiboByUserId(userId);
    }

    /**
     * 根据用户id查询用户发表的微博数量
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:09 2021/5/13
     */
    @RequestMapping("/getWeiboCountByUserId")
    public Result getWeiboCountByUserId(Integer userId) {
        return usersService.getWeiboCountByUserId(userId);
    }

    /**
     * 根据用户id查询用户的关注数量
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:09 2021/5/13
     */
    @RequestMapping("/getFollowCountByUserId")
    public Result getFollowCountByUserId(Integer userId) {
        return usersService.getFollowCountById(userId);
    }

    /**
     * 根据用户id查询用户的粉丝数量
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:09 2021/5/13
     */
    @RequestMapping("/getFollowerCountByUserId")
    public Result getFollowerCountByUserId(Integer userId) {
        return usersService.getFollowerCountById(userId);
    }

    /**
     * 模糊查询用户
     *
     * @param likeName
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:12 2021/5/10
     */
    @RequestMapping("/getUsersByLikeName")
    public Result getUsersByLikeName(String likeName, Integer uid) {
        return usersService.getUsersByLikeName(likeName, uid);
    }

    /**
     * 添加关注
     *
     * @param followId
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:10 2021/5/13
     */
    @RequestMapping("/addFollow")
    public Result addFollow(Integer followId, Integer uid) {
        return usersService.addFollow(followId, uid);
    }

    /**
     * 根据用户id获取用户自己和自己关注的所有微博
     *
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:10 2021/5/13
     */
    @RequestMapping("/getWeiboFromFollowsAndMe")
    public Result getWeiboFromFollowsAndMe(Integer uid) {
        return usersService.getWeiboFromFollowsAndMe(uid);
    }

    /**
     * 根据用户id获取自己所有的关注
     *
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:10 2021/5/13
     */
    @RequestMapping("/getFollowsByUid")
    public Result getFollowsByUid(Integer uid) {
        return usersService.getFollowsByUid(uid);
    }

    /**
     * 根据用户id获取自己所有的粉丝
     *
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:11 2021/5/13
     */
    @RequestMapping("/getFollowersByUid")
    public Result getFollowersByUid(Integer uid) {
        return usersService.getFollowersByUid(uid);
    }

    /**
     * 根据<关注-粉丝关系表id>取消关注关系
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:11 2021/5/13
     */
    @RequestMapping("/deleteFollow")
    public Result deleteFollow(Integer id) {
        return usersService.deleteFollow(id);
    }

    /**
     * 发表评论
     *
     * @param comment
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:13 2021/5/13
     */
    @RequestMapping("/submitCommentInput")
    public Result submitCommentInput(Comment comment) {
        return usersService.submitCommentInput(comment);
    }

    /**
     * 根据微博的id号获取微博的评论
     *
     * @param weiboId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:13 2021/5/13
     */
    @RequestMapping("/getCommentListByWeiboId")
    public Result getCommentListByWeiboId(Integer weiboId) {
        return usersService.getCommentListByWeiboId(weiboId);
    }

    /**
     * 点赞
     *
     * @param weiboLike
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:13 2021/5/13
     */
    @RequestMapping("/submitWeiboLike")
    public Result submitWeiboLike(WeiboLike weiboLike) {
        return usersService.submitWeiboLike(weiboLike);
    }

    /**
     * 取消点赞
     *
     * @param weiboLike
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:14 2021/5/13
     */
    @RequestMapping("/deleteWeiboLike")
    public Result deleteWeiboLike(WeiboLike weiboLike) {
        return usersService.deleteWeiboLike(weiboLike);
    }

    /**
     * 获取系统默认的头像
     *
     * @param
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:14 2021/5/13
     */
    @RequestMapping("/getDefaultAvatar")
    public Result getDefaultAvatar() {
        return userService.getDefaultAvatar();
    }

    /**
     * 用户端注册
     *
     * @param user
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:14 2021/5/13
     */
    @RequestMapping("/insertUser")
    public Result insertUser(User user) {
        return userService.insertUser(user);
    }

    /**
     * 根据用户id修改用户名
     *
     * @param id
     * @param name
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:14 2021/5/13
     */
    @RequestMapping("/updateUserById")
    public Result updateUserById(Integer id, String name) {
        return userService.updateUserById(id, name);
    }

    /**
     * 根据用户id直接删除用户
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:16 2021/5/13
     */
    @RequestMapping("/deleteUserById")
    public Result deleteUserById2(Integer id) {
        return userService.deleteUserById2(id);
    }

    /**
     * 通过微博ID删除微博
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 18:33 2021/5/10
     */
    @RequestMapping("/deleteWeiboById")
    public Result deleteWeiboById(Integer id) {
        return usersService.deleteWeiboById(id);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public Result updateUser(User user) {
        return usersService.updateUser(user);
    }

    /**
     * 设置缓存失效时间，统一为凌晨零点
     *
     * @param hotWord
     * @throws Exception
     */
    @RequestMapping("/addHotWord")
    public void addHotWord(String hotWord) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //晚上十二点与当前时间的毫秒差
        Long timeOut = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        redisTemplate.expire("hotWord", timeOut, TimeUnit.SECONDS);
        redisTemplate.opsForZSet().incrementScore("hotWord", hotWord, 1); // 加入排序set
    }

    /**
     * 获取热词前五位
     *
     * @return
     */
    @RequestMapping("/getHotWord")
    public List<HotWord> getHotWord() {
        List<HotWord> hotWordList = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("hotWord", 1, 100);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = typedTupleSet.iterator();
        int flag = 0;
        while (iterator.hasNext()) {
            flag++;
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            String value = (String) typedTuple.getValue();
            int score = (int) Math.ceil(typedTuple.getScore());
            HotWord hotWord = new HotWord(value, score);
            hotWordList.add(hotWord);
            if (flag >= 5) break;
        }
        return hotWordList;
    }

    /**
     * 查询当前用户是否为会员
     *
     * @param userID
     * @return java.lang.Integer
     * @author HL.
     * @Date 17:25 2021/5/24
     */
    @RequestMapping("/checkIsVip")
    public boolean checkIsVip(Integer userID) {
        return usersService.checkIsVip(userID);
    }

}
