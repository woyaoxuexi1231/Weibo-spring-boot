package com.cqut.weibo.security.service.serviceImpl;

import com.cqut.weibo.dao.UserMapper;
import com.cqut.weibo.dao.WeiboImageMapper;
import com.cqut.weibo.dao.WeiboMapper;
import com.cqut.weibo.dao.WeiboVideoMapper;
import com.cqut.weibo.dto.ImageListDto;
import com.cqut.weibo.dto.Result;
import com.cqut.weibo.dto.WeiboDto;
import com.cqut.weibo.security.dto.AuthorityUserDto;
import com.cqut.weibo.security.dto.UserDto;
import com.cqut.weibo.security.pojo.User;
import com.cqut.weibo.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeiboMapper weiboMapper;

    @Autowired
    private WeiboVideoMapper weiboVideoMapper;

    @Autowired
    private WeiboImageMapper weiboImageMapper;

    @Override
    public Result getAllUser(Integer page, Integer limit, String userName) {
        Result<List<UserDto>> result = new Result();
        Integer start = (page - 1) * limit;
        result.setCode(200);
        System.out.println(userName);
        result.setCount(userMapper.getCount(userName));
        result.setMessage("查询成功");
        result.setData(userMapper.selectAll(start, limit, userName));
        return result;
    }

    @Override
    public Result getAllWeibo(Integer page, Integer limit, String weiboText) {
        Result<List<WeiboDto>> result = new Result();
        Integer start = (page - 1) * limit;
        result.setCode(200);
        result.setCount(weiboMapper.getCount(weiboText));
        List<WeiboDto> weibos = weiboMapper.selectAll(start, limit, weiboText);
        result.setMessage("查询成功");
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
     * 用户注册
     *
     * @param user
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:14 2021/5/13
     */
    @Override
    public Result insertUser(User user) {
        Result result = new Result();
        if (checkUserIsExist(user.getUserName(), user.getUserPhone(), user.getId()).getData()) {
            result.setCode(500);
            result.setMessage("创建失败，用户名或手机号已存在");
            return result;
        } else {
            String encode = new BCryptPasswordEncoder(10).encode(user.getPassword());
            user.setPassword(encode);
            userMapper.insertUser(user);
            userMapper.insertUserRole(user.getId());
            result.setCode(200);
            result.setMessage("创建成功");
            return result;
        }
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
     * 获取系统默认的头像
     *
     * @param
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:14 2021/5/13
     */
    @Override
    public Result getDefaultAvatar() {
        Result<String> result = new Result();
        Integer indexOfDefaultAvatar = new Random().nextInt(5) + 1;
        result.setCode(200);
        result.setMessage("查询成功");
        result.setData(userMapper.getDefaultAvatar(indexOfDefaultAvatar));
        return result;
    }

    /**
     * 用过用户id查询用户
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:16 2021/5/10
     */
    @Override
    public Result getUserById(Integer id) {
        Result<UserDto> result = new Result();
        UserDto userDto = userMapper.selectByPrimaryKey(id);
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
     * 更新用户信息
     *
     * @param user
     * @return
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
            UserDto user1 = userMapper.selectByPrimaryKey(user.getId());
            user.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));
            if (user1 != null) {
                System.out.println("更新返回的值" + userMapper.updateUser(user));
            } else {
                result.setCode(500);
                result.setMessage("修改失败，为查找到需要修改的用户");
            }
        }
        return result;
    }

    /**
     * 通过用户id删除用户
     *
     * @param id
     * @param enabled
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:17 2021/5/10
     */
    @Override
    public Result deleteUserById(Integer id, boolean enabled) {
        Result result = new Result();
        System.out.println(enabled);
        userMapper.deleteByPrimaryKey(id, enabled);
        return result;
    }

    /**
     * 通过微博id号删除微博
     *
     * @param id
     * @param deleted
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:17 2021/5/10
     */
    @Override
    public Result deleteWeiboById(Integer id, boolean deleted) {
        Result result = new Result();
        weiboMapper.deleteByPrimaryKey(id, deleted);
        return result;
    }

    /**
     * 通过微博id号锁定微博
     *
     * @param id
     * @param enabled
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:18 2021/5/10
     */
    @Override
    public Result lockWeiboById(Integer id, boolean enabled) {
        Result result = new Result();
        weiboMapper.lockWeiboById(id, enabled);
        return result;
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
    @Override
    public Result updateUserById(Integer id, String name) {
        Result result = new Result();
        Integer test = userMapper.updateUserById(id, name);
        System.out.println(test);
        return result;
    }

    /**
     * 根据用户id直接删除用户
     *
     * @param id
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 14:16 2021/5/13
     */
    @Deprecated
    @Override
    public Result deleteUserById2(Integer id) {
        Integer test = userMapper.deleteUserById2(id);
        System.out.println(test);
        return null;
    }

    /**
     * 权限分配获取所有用户信息
     *
     * @param page
     * @param limit
     * @param userName
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 13:45 2021/5/10
     */
    @Override
    public Result authorityGetAllUser(Integer page, Integer limit, String userName) {
        Result<List<AuthorityUserDto>> result = new Result();
        Integer start = (page - 1) * limit;
        result.setCode(200);
        System.out.println(userName);
        result.setCount(userMapper.getCount(userName));
        result.setMessage("查询成功");
        result.setData(userMapper.authoritySelectAll(start, limit, userName));
        return result;
    }

    /**
     * 通过用户id修改用户权限
     *
     * @param uid
     * @param rid
     * @return com.cqut.weibo.dto.Result
     * @author HL.
     * @Date 15:01 2021/5/10
     */
    @Override
    public Result authorityChange(Integer uid, Integer rid) {
        Result result = new Result();
        userMapper.authorityChange(uid, rid);
        return result;
    }

    /**
     * SpringSecurity重写的方法，登录时读取数据库用户数据进行验证
     *
     * @param userName
     * @return org.springframework.security.core.userdetails.UserDetails
     * @author HL.
     * @date 2021/2/25
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("账户不存在!");
        }
        user.setRoles(userMapper.getUserRolesByUid(user.getId()));
        //System.out.println(user.getRoles().get(0).getName());
        return user;
    }
}
