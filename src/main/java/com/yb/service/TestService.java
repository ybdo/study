package com.yb.service;

import com.yb.pojo.SysUser;

import java.util.Set;

public interface TestService {
    Set<String> queryAllPerms(Long id);
    SysUser selectUserById(Long id);
}
