package com.yb.dao;

import com.yb.pojo.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    int insert(SysRole role);
    int del(int id);
    List<SysRole> rolesById(int userId);
    int update(SysRole role);
    List<SysRole> allRoles();
    int addPerm(Map<String, Map<Integer, Integer>> map);
}