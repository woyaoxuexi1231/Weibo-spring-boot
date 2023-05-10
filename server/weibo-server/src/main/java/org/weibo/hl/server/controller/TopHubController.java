package org.weibo.hl.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weibo.hl.core.pojo.redis.HotWord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.controller
 * @className: TopHubController
 * @description: 热搜
 * @author: h1123
 * @createDate: 2023/5/11 0:08
 */

@RestController
public class TopHubController {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

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
        long timeOut = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
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
}
