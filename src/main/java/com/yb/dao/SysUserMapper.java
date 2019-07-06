package com.yb.dao;

import com.yb.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface SysUserMapper {

    SysUser selectByUserNumber(Integer userName);
    int addUser(SysUser user);
    Integer addRolers(Map<String,Map<Integer,Integer>> map);
}