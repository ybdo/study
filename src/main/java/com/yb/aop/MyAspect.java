package com.yb.aop;

import com.yb.annotation.MyAop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * AOP
 * @author yb
 * @date 2019/4/19 17:00
 */
@Aspect
@Component
public class MyAspect {
    //切点(面向方法)
    /**
     * execution()  用于匹配是连接点的执行方法
     * @annotation() 限定带有指定注解的连接点
     * arg() 限定连接点方法参数
     */
    @Pointcut("@annotation(com.yb.annotation.MyAop)")
    public void pointCut(){}
    //切面
    /**
     * @Before("pointCut()&&args(user)"):将目标对象方法名称为user的参数传递进来
     * JoinPoint 连接点
     *  public void before(JoinPoint point，User user){
     *         point.getArgs();  可获取所有参数
     *     }
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("-----------------");
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取操作名称
        MyAop myAop = method.getAnnotation(MyAop.class);
        System.out.println("操作名称："+myAop.value());
        //获取类名以及方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        System.out.println(className);
        System.out.println(methodName);
        //方法返回值类型
        AnnotatedType returnType = method.getAnnotatedReturnType();
        System.out.println("方法返回值类型--"+returnType);
        //方法参数类型
        Type[] types = method.getGenericParameterTypes();
        //获取所有参数
        Object args[] = joinPoint.getArgs();
        System.out.println("方法形参实参列表----");
        for (int i = 0; i <types.length ; i++) {
            System.out.println(types[i]+"  =  "+args[i]);
        }
        System.out.println("列表结束------");
        System.out.println("before");
        System.out.println("-----------------");
    }
    //环绕通知
    @Around("pointCut()&&args(id)")
    public Object around(ProceedingJoinPoint jp,Integer id)throws Throwable{
        System.out.println(id);
        if(id==2) {
            jp.proceed();
        }else{
            return "pp";
        }
        return "oo";
    }
    @After("pointCut()")
    public void after(){
        System.out.println("after");
    }
    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }
    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }


}
