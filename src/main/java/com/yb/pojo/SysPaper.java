package com.yb.pojo;

import java.io.Serializable;
import java.util.Date;

public class SysPaper implements Serializable {
    private Integer paperId;

    private String name;

    private Integer teaId;

    private Integer state;

    private Integer maker;

    private Date creatTime;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getMaker() {
        return maker;
    }

    public void setMaker(Integer maker) {
        this.maker = maker;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}