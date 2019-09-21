package com.yb.shiro;

import com.yb.pojo.SysUser;
import com.yb.service.TestService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author ybdo
 * @date 2018/10/29 14:24
 */
@Component
public class SysUserRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    TestService testService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取到当前用户
        System.out.println("授权");
        SysUser user = (SysUser)principalCollection.getPrimaryPrincipal();
        Set<String> perms = testService.queryAllPerms(user.getId());
        System.out.println(perms.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(perms);
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        long id = Long.parseLong((String) authenticationToken.getPrincipal());
        String password = new String((char[]) authenticationToken.getCredentials());
        SysUser sysUser = testService.selectUserById(id);
        if(sysUser==null){
            throw new UnknownAccountException("用户名或者密码错误");
        }
        if(!sysUser.getPassWord().equals(password)){
            throw  new IncorrectCredentialsException("用户名或者密码错误");
        }
        if(sysUser.getPassWord()== "1" ){
            throw new LockedAccountException("用户被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser,sysUser.getPassWord(),this.getName());
        return simpleAuthenticationInfo;
    }
}
