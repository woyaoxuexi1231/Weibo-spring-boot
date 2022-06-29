package com.cqut.weibo.service.serviceImpl;

import com.cqut.weibo.dao.CommentMapper;
import com.cqut.weibo.dao.FansMapper;
import com.cqut.weibo.dao.UserMapper;
import com.cqut.weibo.dao.UsersMapper;
import com.cqut.weibo.dao.WeiboImageMapper;
import com.cqut.weibo.dao.WeiboLikeMapper;
import com.cqut.weibo.dao.WeiboMapper;
import com.cqut.weibo.dao.WeiboVideoMapper;
import com.cqut.weibo.dto.CommentDto;
import com.cqut.weibo.dto.FollowDto;
import com.cqut.weibo.dto.FollowerDto;
import com.cqut.weibo.dto.ImageListDto;
import com.cqut.weibo.dto.Result;
import com.cqut.weibo.dto.WeiboDto;
import com.cqut.weibo.dto.WeiboSubmitDto;
import com.cqut.weibo.pojo.Comment;
import com.cqut.weibo.pojo.WeiboLike;
import com.cqut.weibo.security.dto.UserDto;
import com.cqut.weibo.security.pojo.User;
import com.cqut.weibo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeiboMapper weiboMapper;

    @Autowired
    private FansMapper fansMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private WeiboLikeMapper weiboLikeMapper;

    @Autowired
    private WeiboVideoMapper weiboVideoMapper;

    @Autowired
    private WeiboImageMapper weiboImageMapper;

    /**
     * 通过用户id查询用户信息
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:04 2021/5/13
     */
    @Override
    public Result getUserById(Integer id) {
        Result<UserDto> result = new Result();
        UserDto userDto = usersMapper.selectByPrimaryKey(id);
        if (userDto != null) {
            result.setCode(200);
            result.setMessage("查询成功");
            result.setData(userDto);
        } else {
            result.setCode(500);
            result.setMessage("为查询到该用户");
        }
        return result;
    }

    /**
     * 发表微博
     *
     * @param weiboSubmitDto
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:05 2021/5/13
     */
    @Override
    public Result submitWeibo(WeiboSubmitDto weiboSubmitDto) {

        //for (int i = 0; i < weiboSubmitDto.getImageNameList().length; i++) {
        //    System.out.println(weiboSubmitDto.getImageNameList()[i]);
        //    System.out.println(weiboSubmitDto.getImageURLList()[i]);
        //}

        Date date = new Date();  // 获取当前系统时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // 设置日期格式
        String createTime = simpleDateFormat.format(date);
        Result result = new Result();
        weiboMapper.insert(weiboSubmitDto);
        weiboMapper.insertCreateTime(weiboSubmitDto.getId(), createTime);
        for (int i = 0; i < weiboSubmitDto.getImageNameList().length; i++) {
            weiboMapper.submitWeiboImages(weiboSubmitDto.getId(), weiboSubmitDto.getImageNameList()[i], weiboSubmitDto.getImageURLList()[i]);
        }
        if (weiboSubmitDto.getVideoName() != "") {
            weiboMapper.submitWeiboVideo(weiboSubmitDto.getId(), weiboSubmitDto.getVideoName(), weiboSubmitDto.getVideoURL());
        }
        result.setMessage("发布成功");
        return result;
    }

    /**
     * 通过用户id查询微博（当前id的所有微博）
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:05 2021/5/13
     */
    @Override
    public Result getWeiboByUserId(Integer userId) {
        Result<List<WeiboDto>> result = new Result<>();
        List<WeiboDto> weibos = weiboMapper.selectByUserId(userId);
        //查询当前用户点赞的微博有哪些
        for (int i = 0; i < weibos.size(); i++) {
            if (weiboLikeMapper.selectWeiboLikeState(weibos.get(i).getId(), userId) != null) {
                weibos.get(i).setLikeState(true);
            }
        }
        //查询当前微博是否含有图片
        for (int i = 0; i < weibos.size(); i++) {
            List<ImageListDto> weiboImageList = weiboImageMapper.selectByWeiboId(weibos.get(i).getId());
            if (weiboImageList.size() != 0) {
                weibos.get(i).setImageList(weiboImageList);
            }
        }
        //查询当前微博是否含有视频
        for (int i = 0; i < weibos.size(); i++) {
            weibos.get(i).setVideo(weiboVideoMapper.selectByWeiboId(weibos.get(i).getId()));
        }
        result.setCode(200);
        result.setMessage("查询成功");
        result.setData(weibos);
        return result;
    }

    /**
     * 根据用户id查询用户发表的微博数量
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:09 2021/5/13
     */
    @Override
    public Result getWeiboCountByUserId(Integer userId) {
        Result<Integer> result = new Result<>();
        result.setCount(usersMapper.getWeiboCountByUserId(userId));
        return result;
    }

    /**
     * 根据用户id查询用户的关注数量
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:09 2021/5/13
     */
    @Override
    public Result getFollowCountById(Integer userId) {
        Result<Integer> result = new Result<>();
        result.setCount(usersMapper.getFollowCountById(userId));
        return result;
    }

    /**
     * 根据用户id查询用户的粉丝数量
     *
     * @param userId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:09 2021/5/13
     */
    @Override
    public Result getFollowerCountById(Integer userId) {
        Result<Integer> result = new Result<>();
        result.setCount(usersMapper.getFollowerCountById(userId));
        return result;
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
    @Override
    public Result getUsersByLikeName(String likeName, Integer uid) {
        Result<List<UserDto>> result = new Result<>();
        List<UserDto> userDtos = usersMapper.getUsersByLikeName(likeName, uid);
        List<Integer> followList = usersMapper.getFollowList(uid);
        for (int i = 0; i < userDtos.size(); ++i) {
            userDtos.get(i).setFollowCount(usersMapper.getFollowCountById(userDtos.get(i).getId()));
            userDtos.get(i).setFollowerCount(usersMapper.getFollowerCountById(userDtos.get(i).getId()));
            userDtos.get(i).setWeiboCount(usersMapper.getWeiboCountByUserId(userDtos.get(i).getId()));
            for (int j = 0; j < followList.size(); j++) {
                if (userDtos.get(i).getId().equals(followList.get(j))) {
                    userDtos.get(i).setFollow(true);
                    break;
                }
            }
        }
        result.setData(userDtos);
        return result;
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
    @Override
    public Result addFollow(Integer followId, Integer uid) {
        Result result = new Result();
        Integer isFollowId = fansMapper.isFollow(followId, uid);
        if (isFollowId != null) {
            fansMapper.updateByPrimaryKey(isFollowId);
        } else {
            fansMapper.insert(followId, uid);
        }
        return result;
    }

    /**
     * 根据用户id获取用户自己和自己关注的所有微博
     *
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:10 2021/5/13
     */
    @Override
    public Result getWeiboFromFollowsAndMe(Integer uid) {
        Result<List<WeiboDto>> result = new Result<>();
        List<WeiboDto> weibos = weiboMapper.getWeiboFromFollowsAndMe(uid);
        //查询当前用户点赞的微博有哪些
        for (int i = 0; i < weibos.size(); i++) {
            if (weiboLikeMapper.selectWeiboLikeState(weibos.get(i).getId(), uid) != null) {
                weibos.get(i).setLikeState(true);
            }
        }
        //查询当前微博是否含有图片
        for (int i = 0; i < weibos.size(); i++) {
            List<ImageListDto> weiboImageList = weiboImageMapper.selectByWeiboId(weibos.get(i).getId());
            if (weiboImageList.size() != 0) {
                weibos.get(i).setImageList(weiboImageList);
            }
        }
        //查询当前微博是否含有视频
        for (int i = 0; i < weibos.size(); i++) {
            weibos.get(i).setVideo(weiboVideoMapper.selectByWeiboId(weibos.get(i).getId()));
        }
        result.setData(weibos);
        return result;
    }

    /**
     * 根据用户id获取自己所有的关注
     *
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:10 2021/5/13
     */
    @Override
    public Result getFollowsByUid(Integer uid) {
        Result<List<FollowDto>> result = new Result<>();
        List<FollowDto> followDtos = usersMapper.getFollowsByUid(uid);
        result.setData(followDtos);
        return result;
    }

    /**
     * 根据用户id获取自己所有的粉丝
     *
     * @param uid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:11 2021/5/13
     */
    @Override
    public Result getFollowersByUid(Integer uid) {
        Result<List<FollowerDto>> result = new Result<>();
        List<FollowerDto> followerDtos = usersMapper.getFollowersByUid(uid);
        List<Integer> followList = usersMapper.getFollowList(uid);
        for (int i = 0; i < followerDtos.size(); i++) {
            for (int j = 0; j < followList.size(); j++) {
                if (followerDtos.get(i).getFid() == followList.get(j)) {
                    followerDtos.get(i).setFollow(true);
                    break;
                }
            }
        }
        result.setData(followerDtos);
        return result;
    }

    /**
     * 根据<关注-粉丝关系表id>取消关注关系
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:11 2021/5/13
     */
    @Override
    public Result deleteFollow(Integer id) {
        Result result = new Result();
        System.out.println(id);
        fansMapper.deleteFollow(id);
        return result;
    }

    /**
     * 发表评论
     *
     * @param comment
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:13 2021/5/13
     */
    @Override
    public Result submitCommentInput(Comment comment) {
        Result result = new Result();
        Date date = new Date();  // 获取当前系统时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String createTime = simpleDateFormat.format(date);
        commentMapper.insert(comment, createTime);
        weiboMapper.updateCommentCountByWeiboId(comment.getWeiboId());
        return result;
    }

    /**
     * 根据微博的id号获取微博的评论
     *
     * @param weiboId
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:13 2021/5/13
     */
    @Override
    public Result getCommentListByWeiboId(Integer weiboId) {
        Result<List<CommentDto>> result = new Result<>();
        List<CommentDto> commentList = commentMapper.selectByWeiboId(weiboId);
        result.setData(commentList);
        return result;
    }

    /**
     * 点赞
     *
     * @param weiboLike
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:13 2021/5/13
     */
    @Override
    public Result submitWeiboLike(WeiboLike weiboLike) {
        Result result = new Result();
        weiboLikeMapper.insert(weiboLike);
        weiboMapper.updateLikeCountByWeiboId(weiboLike.getWeiboId());
        return result;
    }

    /**
     * 取消点赞
     *
     * @param weiboLike
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:14 2021/5/13
     */
    @Override
    public Result deleteWeiboLike(WeiboLike weiboLike) {
        Result result = new Result();
        weiboLikeMapper.delete(weiboLike);
        weiboMapper.updateLikeCountByWeiboId(weiboLike.getWeiboId());
        return result;
    }

    /**
     * 普通用户通过微博id删除微博
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 18:39 2021/5/10
     */
    @Override
    public Result deleteWeiboById(Integer id) {
        Result result = new Result();
        weiboMapper.deleteWeiboById(id);
        return result;
    }

    /**
     * 普通用户更新自己的用户
     *
     * @param user
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 19:49 2021/5/10
     */
    @Override
    public Result updateUser(User user) {
        //System.out.println(new BCryptPasswordEncoder(10).encode(password));
        Result result = new Result();
        if (checkUserIsExist(user.getUserName(), user.getUserPhone(), user.getId()).getData()) {
            result.setCode(500);
            result.setMessage("更新失败，用户名或手机号已存在");
            return result;
        } else {
            UserDto user1 = usersMapper.selectByPrimaryKey(user.getId());
            if (user1 != null) {
                System.out.println("更新返回的值" + usersMapper.updateUser(user));
            } else {
                result.setCode(500);
                result.setMessage("修改失败，为查找到需要修改的用户");
            }
        }
        return result;
    }

    /**
     * 查询用户是否已经存在
     *
     * @param userName
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:14 2021/5/10
     */
    @Override
    public Result<Boolean> checkUserIsExist(String userName, String userPhone, Integer uid) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("查询成功");
        if (userMapper.checkUserIsExist(userName, userPhone, uid).size() != 0) {
            result.setData(true);
        } else {
            result.setData(false);
        }
        return result;
    }

    /**
     * 添加订单信息（会员充值）
     *
     * @param map
     * @return void
     * @author HL.
     * @Date 14:03 2021/5/13
     */
    @Override
    public String addOrderInformation(Map map) throws ParseException {
        Result result = new Result();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //订单号
        String[] outTradeNoArray = (String[]) map.get("out_trade_no");
        //订单创建时间作为起始时间
        String[] gmtCreateArray = (String[]) map.get("gmt_create");
        Date gmtCreate = dateFormat.parse(gmtCreateArray[0]);
        //获取金额
        String[] totalAmountArray = (String[]) map.get("total_amount");
        //获取充值会员的用户id
        Integer userID = Integer.parseInt(((String[]) map.get("body"))[0]);
        //获取订单信息
        String[] subjectArray = (String[]) map.get("subject");
        //获取订单开始的年份
        int startYear = Integer.parseInt(gmtCreateArray[0].substring(0, 4));
        //获取订单开始的月份
        int startMonth = Integer.parseInt(gmtCreateArray[0].substring(5, 7));
        //计算充值的时长
        int time = (int) (Double.parseDouble(totalAmountArray[0]) / 20);
        //计算结束时间
        StringBuilder endTime = new StringBuilder(gmtCreateArray[0]);
        if (time == 12) {
            endTime.replace(0, 4, "" + (startYear + 1));
        } else {
            if (time + startMonth > 12) {
                endTime.replace(0, 4, "" + (startYear + 1));
                endTime.replace(5, 7, "" + (time + startMonth - 12));
            } else {
                endTime.replace(5, 7, "" + (time + startMonth));
            }
        }
        //存订单信息
        usersMapper.addOrderInformation(outTradeNoArray[0], gmtCreate, dateFormat.parse(endTime.toString()), userID, (int) (Double.parseDouble(totalAmountArray[0])), subjectArray[0]);
        //查看该用户的会员信息
        if (isVip(userID)) {
            //如果是会员，延长过期时间
            StringBuilder endTimeBefore = new StringBuilder(usersMapper.getEndTime(userID));
            //获取订单开始的年份
            int startYearBefore = Integer.parseInt(endTimeBefore.substring(0, 4));
            //获取订单开始的月份
            int startMonthBefore = Integer.parseInt(endTimeBefore.substring(5, 7));
            if (time == 12) {
                endTimeBefore.replace(0, 4, "" + (startYearBefore + 1));
            } else {
                if (time + startMonth > 12) {
                    endTimeBefore.replace(0, 4, "" + (startYearBefore + 1));
                    endTimeBefore.replace(5, 7, "" + (time + startMonthBefore - 12));
                } else {
                    endTimeBefore.replace(5, 7, "" + (time + startMonthBefore));
                }
            }
            usersMapper.updateVipInformation(dateFormat.parse(endTimeBefore.toString()), userID);
        }
        //如果不是会员
        else {
            //查询是否之前充值过
            if (usersMapper.checkIsVipBefore(userID) != null) {
                usersMapper.updateVipInformation(dateFormat.parse(endTime.toString()), userID);
            } else {
                usersMapper.addVipInformation(dateFormat.parse(endTime.toString()), userID);
            }
        }
        return "success";
    }

    /**
     * 查询当前用户是否为会员
     *
     * @param userID
     * @return java.lang.Integer
     * @author HL.
     * @Date 17:25 2021/5/24
     */
    @Override
    public boolean checkIsVip(Integer userID) {
        return isVip(userID);
    }

    /**
     * 验证当前用户是否为会员(充值会员内部功能)
     *
     * @param userID
     * @return boolean
     * @author HL.
     * @Date 15:03 2021/5/24
     */
    private boolean isVip(Integer userID) {
        if (usersMapper.checkIsVip(userID) != null) {
            return true;
        } else {
            return false;
        }
    }

}
