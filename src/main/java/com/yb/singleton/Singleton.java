package com.yb.singleton;

//反射安全
//线程安全
//延迟加载
//调用:Singleton.INSTANCE.getInstance();

import com.yb.pojo.SysUser;

public enum Singleton {
    INSTANCE;
    private SysUser user;
    private void newSysUser(){
        user=new SysUser();
        user.setUserName("ybdo");
        user.setPassWord("123");
    }
    public SysUser getInstance(){
       return user;
    }
}
