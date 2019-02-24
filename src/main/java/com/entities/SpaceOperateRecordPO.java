package com.entities;

import java.util.Date;

//空间操作记录
public class SpaceOperateRecordPO {
    //操作记录id
    private long id;
    //空间id
    private long spaceId;
    //操作人id
    private long operatorId;
    //操作时间
    private Date operaterTime;
    //操作类型（1、创建空间，2、更改权限，3、新建页面，4、修改页面，5、删除页面）
    private Integer type;
    //操作内容
    private String operaterContent;
    private boolean expired;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(long spaceId) {
        this.spaceId = spaceId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(Date operaterTime) {
        this.operaterTime = operaterTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperaterContent() {
        return operaterContent;
    }

    public void setOperaterContent(String operaterContent) {
        this.operaterContent = operaterContent;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
