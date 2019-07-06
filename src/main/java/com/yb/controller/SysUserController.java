package com.yb.controller;

import com.yb.pojo.SysUser;
import com.yb.service.SysRoleService;
import com.yb.service.SysUserService;
import com.yb.util.Message;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yb
 * @date 2019/6/1
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    SysUserService userService;
    @Autowired
    SysRoleService roleService;

    //测试用户
    @RequestMapping("/save")
    public void test(SysUser user) {
//        user.setClassId(1601);
//        user.setCreatUserId(-1);
//        user.setEmail("YB456137@163.com");
//        user.setFlage(0);
//        user.setStatus(0);
//        user.setMobile("18590737757");
//        user.setName("admin");
//        user.setNumber(16091116);
//        user.setPassword("123");
//        Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getNumber().toString(), 1024);
//        user.setPassword(md5Hash.toHex());
//        boolean b = userService.addUser(user);
        user.setClassId(1601);
        user.setCreatUserId(1);
        user.setEmail("YB456137@163.com");
        user.setFlage(1);
        user.setStatus(0);
        user.setMobile("18590737757");
        user.setName("yb");
        user.setNumber(16091117);
        user.setPassword("123");
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getNumber().toString(), 1024);
        user.setPassword(md5Hash.toHex());
        boolean b = userService.addUser(user);
    }
    //根据学号查询用户
    @RequestMapping("/getUser")
    public Message getUser(Integer number){
        SysUser sysUser = userService.selectByUserNumber(number);
        return Message.ok().put("user",sysUser);
    }
    //当前用户所拥有的权限
    @RequestMapping("/perm")
    public Message perm(){
        return Message.ok().put("roles",userService);
    }
    //当前用户所拥有的角色
    @RequestMapping("/role")
    public Message role(){
        return Message.ok().put("roles",   roleService.rolesById());
    }

    //给用户添加角色
    @RequestMapping("/addRolers")
    public Message addRolers(Integer[] roleId) {
        if (userService.addRolers(roleId)) {
            return Message.ok();
        }
        return Message.error("插入失败");
    }
}
