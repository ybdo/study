package com.yb.util;

import com.yb.pojo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {

    //获取当前的session
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    //获取当前主体
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    //获取当前user对象   看你传入的参数是什么SimpleAuthenticationInfo
    public static SysUser getUserEntity() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }
    //获取当前用户的id
    public static Integer getUserId() {
        return 2;
        //return getUserEntity().getId();
    }
    //将需要的值存入session中
    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    //从session中获取值
    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    //判断用户是否登陆
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    //让用户退出
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    //判断验证码
    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if (kaptcha == null) {
            throw new MyException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }

}
