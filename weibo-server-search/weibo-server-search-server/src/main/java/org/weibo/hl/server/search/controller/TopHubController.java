package org.weibo.hl.server.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weibo.hl.core.pojo.search.HotWord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.search.controller
 * @className: TopHubController
 * @description: 热搜
 * @author: hl
 * @createDate: 2023/5/11 0:08
 */

@RestController
public class TopHubController {

    @Autowired
    @Qualifier(value = "redisTemplate")
    RedisTemplate<Object, Object> redisTemplate;

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
