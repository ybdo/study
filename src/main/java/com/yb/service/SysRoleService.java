package com.yb.service;

import com.yb.pojo.SysRole;

import java.util.List;

public interface SysRoleService {
    //添加角色
    boolean insert(SysRole role);
    boolean del(int id);
    //查询该用户所拥有的角色列表
    List<SysRole> rolesById();
    boolean update(SysRole role);
    //查询所有角色列表
    List<SysRole> allRoles() throws Exception;
    //给角色加权限
    boolean addPerm(Integer roleId,Integer[] menuId);

}
