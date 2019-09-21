package com.yb.redis;

import com.yb.pojo.SysUser;
import com.yb.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yb
 * @date 2019/6/13
 */
@RestController
@RequestMapping("/redis")
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate=null;
    @Autowired
    private StringRedisTemplate stringRedisTemplate=null;

    @RequestMapping("/test")
    public Message testString(){
        redisTemplate.opsForValue().set("key1","value1");
//        (List)redisTemplate.execute((RedisOperations operations=new RedisOperations() {
//            operations.watch("key1");
//            return operations.exec();
//        } );

//        redisTemplate.opsForValue().set("int_key","1");
//        Object key1 = redisTemplate.opsForValue().get("key1");
//        System.out.println(key1.toString());
        stringRedisTemplate.opsForValue().set("int","1");
//        String anInt = stringRedisTemplate.opsForValue().get("int");
//        System.out.println(anInt);
//        stringRedisTemplate.opsForValue().increment("int",1);
//        String anInt1 = stringRedisTemplate.opsForValue().get("int");
//        System.out.println(anInt1);
//        Jedis jedis=(Jedis)stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
//        jedis.decr("int");
//        HashMap<String,String> map=new HashMap<>();
//        map.put("field1","value1");
//        map.put("field2","value2");
//        stringRedisTemplate.opsForHash().putAll("map",map);
//        stringRedisTemplate.opsForHash().put("map","field3","value3");
//        BoundHashOperations hashOperations =stringRedisTemplate.boundHashOps("map");
//        Map<String, String> map1 = jedis.hgetAll("map");
//        Set<String> set = map1.keySet();
//        for (String str:set) {
//            System.out.println(map1.get(str));
//        }
//        hashOperations.delete("field1");
//        hashOperations.put("field4","value5");
//        Map<String, String> map2 = jedis.hgetAll("map");
//        Set<String> set2 = map2.keySet();
//        for (String str:set2) {
//            System.out.println(map2.get(str));
//        }
        //通过redisTemplate进行回调，用一条连接执行多条redis命令
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForValue().set("sss","ddd");
                redisOperations.opsForValue().set("s","ddd");
                return null;
            }
        });
        return Message.ok();
    }
    @RequestMapping("/test1")
    public Message test1(){
        SysUser user = (SysUser)redisTemplate.opsForValue().get("id");
        System.out.println(user.getNumber());
        return Message.ok();
    }

}
