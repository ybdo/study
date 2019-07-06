package com.yb.service;

import com.yb.pojo.SysMenu;
import com.yb.pojo.SysRole;

import java.util.List;
import java.util.Set;

public interface SysMenuService {
    //查询当前用户的所有权限
    Set<String>  queryAllPerms(Integer id);
    //插入menu
    boolean insert(SysMenu menu);
    //根据角色查询menu列表
    List<SysMenu> menusByRole(List<SysRole> list);

}
