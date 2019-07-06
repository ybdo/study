package com.yb.service.impl;

import com.yb.dao.SysRoleMapper;
import com.yb.pojo.SysRole;
import com.yb.service.SysRoleService;
import com.yb.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author yb
 * @date 2019/6/1
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    SysRoleMapper roleMapper;
    @Override
    public boolean insert(SysRole role) {
        if(roleMapper.insert(role)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean del(int id) {
        if(roleMapper.del(id)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<SysRole> rolesById() {
        int userId= ShiroUtils.getUserId();
        return roleMapper.rolesById(userId);
    }

    @Override
    public boolean update(SysRole role) {
        if(roleMapper.update(role)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<SysRole> allRoles() throws Exception{
        return roleMapper.allRoles();
    }

    @Override
    public boolean addPerm(Integer roleId, Integer[] menuId) {
        Map<String, Map<Integer, Integer>> map1 = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int menuid:menuId) {
            map.put(menuid,roleId);
        }
        map1.put("keys",map);
        if(roleMapper.addPerm(map1)>0){
            return true;
        }
        return false;
    }
}
