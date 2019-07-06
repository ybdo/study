package com.yb.service.impl;

import com.yb.dao.SysMenuMapper;
import com.yb.pojo.SysMenu;
import com.yb.pojo.SysRole;
import com.yb.service.SysMenuService;
import com.yb.util.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yb
 * @date 2019/5/30
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    SysMenuMapper menuMapper;

    //查询所有菜单列表
    public List<SysMenu> queryListAll() {
        return menuMapper.queryListAll();
    }

    //查询权限列表
    @Override
    public Set<String> queryAllPerms(Integer userId) {
        if(userId==null){
            userId= ShiroUtils.getUserId();
        }
        List<String> permsList ;
        if (menuMapper.isRoot(userId)==0) {
            List<SysMenu> sysMenus = queryListAll();
            permsList = new ArrayList<>(sysMenus.size());
            for (SysMenu sysMenu : sysMenus) {
                permsList.add(sysMenu.getPerms());
            }
        } else {
            permsList = menuMapper.queryAllPerms(userId);
        }
        Set<String> set = new HashSet<>();
        for (String perm : permsList) {
            if (StringUtils.isBlank(perm)) {
                continue;
            }
            set.addAll(Arrays.asList(perm.trim().split(",")));
        }
        return set;
    }

    @Override
    public boolean insert(SysMenu menu) {
        if(menuMapper.insert(menu)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<SysMenu> menusByRole(List<SysRole> list) {
        List<Integer> list1 =new LinkedList<>();
        for (SysRole r :list) {
            list1.add(r.getRoleId());
        }
        menuMapper.menusByRole(list1);
        return null;
    }


}
