package com.yb.service;

import com.yb.pojo.SysUser;

public interface SysUserService {
    SysUser selectByUserNumber(int number);
    boolean addUser(SysUser user);
    boolean addRolers(Integer[] id);
}
