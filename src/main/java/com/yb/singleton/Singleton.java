package com.yb.singleton;

import com.yb.pojo.SysUser;
//反射安全
//线程安全
//延迟加载
//调用:Singleton.INSTANCE.getInstance();

public enum Singleton {
    INSTANCE;
    private SysUser user;
    private void newSysUser(){
        user=new SysUser();
        user.setStatus(1);
        user.setId(0);
    }
    public SysUser getInstance(){
       return user;
    }
}
