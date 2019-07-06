package com.yb.dao;

import com.yb.pojo.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    List<SysMenu> queryListAll();
    List<String> queryAllPerms(int userId);
    int isRoot(int id);
    int insert(SysMenu menu);
    List<SysMenu> menusByRole(List<Integer> list);
}