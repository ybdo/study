package com.yb.condition;

import com.yb.pojo.SysUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 条件装配bean
 *
 * @author yb
 * @date 2019/6/20
 */
@Configuration
public class Condition {

    @Bean("testUser")
    @Conditional(TestUser.class)
    public SysUser getUser(@Value("${spring.datasource.username}") String username){
        System.out.println(username);
        return new SysUser();
    }
}
