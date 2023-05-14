package org.weibo.hl.server.search.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @projectName: weibo
 * @package: org.weibo.hl.server.config
 * @className: CacheConfig
 * @description:
 * @author: hl
 * @createDate: 2023/5/11 0:11
 */

@Configuration
public class CacheConfig {

    /**
     * 设置 redisTemplate 的编码
     *
     * @param redisTemplate
     */
    @Autowired
    public void redisTemplateSerializerInit(RedisTemplate<Object, Object> redisTemplate) {
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
    }
}
