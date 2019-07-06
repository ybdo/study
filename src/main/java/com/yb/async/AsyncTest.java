package com.yb.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步调用测试
 *
 * @author yb
 * @date 2019/6/21
 */
@Component
public class AsyncTest {
    @Async
    public  void test(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("kp");
    }
}
