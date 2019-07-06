package com.yb.annotation;
/**
 * 自定义注解
 *
 * @author yb
 * @date 2019/4/19 17:00
 */

import java.lang.annotation.*;
/**
 * 自定义注解
 */

/**
 * @Target 所有标注了指定注解的类
 * @Retention 注解说明, 这种类型的注解会被保留到哪个阶段.
 * @Documented 注解表明这个注解应该被 javadoc工具记录.
 * @interface 定义一个注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAop {
    String value();
}
