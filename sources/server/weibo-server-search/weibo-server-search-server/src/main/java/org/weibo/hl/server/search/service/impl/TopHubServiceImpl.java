package org.weibo.hl.server.search.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.weibo.hl.server.search.api.service.TopHubService;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.search.service.impl
 * @className: TopHubServiceImpl
 * @description:
 * @author: hl
 * @createDate: 2023/5/14 20:19
 */

@Service
public class TopHubServiceImpl implements TopHubService {

    @Autowired
    @Qualifier(value = "redisTemplate")
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void addHotWord(String hotWord) {

        // 设置缓存失效时间, 统一为凌晨零点
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 晚上十二点与当前时间的毫秒差
        long timeOut = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;

        redisTemplate.expire("hotWord", timeOut, TimeUnit.SECONDS);
        // 加入排序 set
        redisTemplate.opsForZSet().incrementScore("hotWord", hotWord, 1);
    }
}
