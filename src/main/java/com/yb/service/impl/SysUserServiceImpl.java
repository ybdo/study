package com.yb.service.impl;

import com.yb.dao.SysMenuMapper;
import com.yb.dao.SysRoleMapper;
import com.yb.dao.SysUserMapper;
import com.yb.pojo.SysUser;
import com.yb.service.SysUserService;
import com.yb.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yb
 * @date 2019/5/30
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    SysMenuMapper menuMapper;
    int  userId = ShiroUtils.getUserId();


    @Override
    @Cacheable(cacheNames = "redisCacheManager",key="#number")
    public SysUser selectByUserNumber(int number) {
        System.out.println(number);
        return userMapper.selectByUserNumber(number);
    }

    public boolean addUser(SysUser user) {
        if (userMapper.addUser(user) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addRolers(Integer[] id) {

        Map<String, Map<Integer, Integer>> map1 = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : id) {
            map.put(i, userId);
        }
        map1.put("keys", map);
        if (userMapper.addRolers(map1) > 0) {
            return true;
        }
        return false;
    }


}
