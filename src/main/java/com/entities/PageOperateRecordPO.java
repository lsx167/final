package com.entities;

import java.util.Date;

//页面操作记录
public class PageOperateRecordPO {
    //操作记录id
    private long id;
    //页面id
    private long pageId;
    //操作人id
    private long operatorId;
    //操作时间
    private String operaterTime;
    //操作类型（1、创建页面，2、修改页面，3、修改权限）
    private Integer type;
    //操作前版本号
    private String beforeVersionId;
    //操作后版本号
    private String afterVersionId;
    private boolean expired;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(String operaterTime) {
        this.operaterTime = operaterTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBeforeVersionId() {
        return beforeVersionId;
    }

    public void setBeforeVersionId(String beforeVersionId) {
        this.beforeVersionId = beforeVersionId;
    }

    public String getAfterVersionId() {
        return afterVersionId;
    }

    public void setAfterVersionId(String afterVersionId) {
        this.afterVersionId = afterVersionId;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
