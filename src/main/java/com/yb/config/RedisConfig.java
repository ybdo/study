package com.yb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.time.Duration;

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
    //连接工厂
    @Autowired
    private RedisConnectionFactory redisConnectionFactory=null;
    //redis消息监听器
    @Autowired
    private MessageListener messageListener=null;
    //任务池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler=null;
    //定义自定义后初始化方法
    @PostConstruct
    public void init(){
        initRedisTemplate();
    }

    //设置RedisTemplate的序列化器
    private void initRedisTemplate() {
        RedisSerializer stringSerializer=redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
    }
    //自定义redis缓存管理器
    @Bean("redisCacheManager")
    public RedisCacheManager initRedisCacheManager(){
        //redis的加锁写入器
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory);
        //启动redis缓存的默认设置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        //禁用前缀
        config = config.disableKeyPrefix();
        //设置jdk序列化器
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
        //设置超时时间 10 分钟
        config.entryTtl(Duration.ofMinutes(10));
        //创建redis缓存管理器
        RedisCacheManager redisCacheManager = new RedisCacheManager(writer, config);
        return redisCacheManager;
    }
    //创建任务池，运行线程等待处理redis的消息
    @Bean
    public ThreadPoolTaskScheduler initTaskScheduler(){
        if(threadPoolTaskScheduler!=null){
            return threadPoolTaskScheduler;
        }
        threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        return threadPoolTaskScheduler;
    }
    //定义redis的监听容器
    //@Bean
    public RedisMessageListenerContainer initRedisMessageListener(){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        //redis连接工厂
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        //设置运行任务池
        redisMessageListenerContainer.setTaskExecutor(initTaskScheduler());
        //定义监听渠道
        ChannelTopic yb = new ChannelTopic("yb");
        //使用监听器监听redis消息
        redisMessageListenerContainer.addMessageListener(messageListener,yb);
        return redisMessageListenerContainer;
    }

}
