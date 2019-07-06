package com.yb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

/**
 * redis配置
 *
 * @author yb
 * @date 2019/6/13
 */
@Configuration
public class RedisConfig {
    //注入RedisTemplate
    @Autowired
    private RedisTemplate redisTemplate=null;

    //定义自定义后初始化方法
    @PostConstruct
    public void init(){
        initRedisTemplate();
    }

    //设置RedisTemplate的序列化去器
    private void initRedisTemplate() {
        RedisSerializer stringSerializer=redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
    }

}
