package com.yb.controller;

import com.yb.pojo.SysMenu;
import com.yb.service.SysMenuService;
import com.yb.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author yb
 * @date 2019/6/1
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    SysMenuService menuService;

    @RequestMapping("/save")
    public Message save(SysMenu menu){
        menu.setName("删除");
        menu.setPerms("sys:del");
        menu.setType(0);
        menu.setUrl("/");
        menu.setParentId(1);
        if(menuService.insert(menu)){
            return Message.ok();
        }
       return Message.error("插入失败");
    }
    //得到id的所有权限
    @RequestMapping("/perms")
    public Message perms(Integer id){
        Set<String> set = menuService.queryAllPerms(id);
        return Message.ok().put("perms",set);
    }

}
