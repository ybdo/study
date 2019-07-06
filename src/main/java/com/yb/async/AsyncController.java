package com.yb.async;

import com.yb.annotation.MyAop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异步测试
 *
 * @author yb
 * @date 2019/6/21
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    @Autowired
    AsyncTest asyncTest;
    @RequestMapping("/around")
    @MyAop("测试")
    public String around(Integer id,String str){
        asyncTest.test();
        System.out.println("ii");
        return "qq";
    }
}
