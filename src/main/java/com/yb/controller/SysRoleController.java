package com.yb.controller;

import com.yb.pojo.SysRole;
import com.yb.service.SysRoleService;
import com.yb.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yb
 * @date 2019/6/1
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    SysRoleService roleService;

    @RequestMapping("/save")
    public Message save(SysRole role){
        role.setCreatUserId(1);
        role.setRoleName("测试用户");
        role.setRemark("测试");
        if(roleService.insert(role)){
            return Message.ok();
        }
       return Message.error("保存失败");
    }


    //展示所有的角色列表
    @RequestMapping("/showAll")
    public Message show() throws Exception{
        return Message.ok().put("roles", roleService.allRoles());
    }
    //角色加权
    @RequestMapping("/addPerm")
    public Message addPerm(Integer roleId,Integer[] menuId){
        if(roleService.addPerm(roleId,menuId)){
            return Message.ok();
        }
        return Message.error("添加权限失败");
    }
}
