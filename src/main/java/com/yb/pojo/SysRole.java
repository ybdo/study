package com.yb.pojo;

import java.io.Serializable;

public class SysRole implements Serializable {
    private Integer roleId;

    private String roleName;

    private String remark;

    private Integer creatUserId;

    private String creatTime;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreatUserId() {
        return creatUserId;
    }

    public void setCreatUserId(Integer creatUserId) {
        this.creatUserId = creatUserId;
    }

    public String   getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String   creatTime) {
        this.creatTime = creatTime;
    }
}