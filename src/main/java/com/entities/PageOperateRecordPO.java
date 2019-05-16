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
    private String operatorTime;
    //操作类型（1、创建页面，2、修改页面，3、修改权限，4、删除页面）
    private Integer type;
    //操作备注
    private String operatorContent;
    //操作前版本号
    private double beforeVersionId;
    //操作后版本号
    private double afterVersionId;
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

    public String getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(String operatorTime) {
        this.operatorTime = operatorTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperatorContent() {
        return operatorContent;
    }

    public void setOperatorContent(String operatorContent) {
        this.operatorContent = operatorContent;
    }

    public double getBeforeVersionId() {
        return beforeVersionId;
    }

    public void setBeforeVersionId(double beforeVersionId) {
        this.beforeVersionId = beforeVersionId;
    }

    public double getAfterVersionId() {
        return afterVersionId;
    }

    public void setAfterVersionId(double afterVersionId) {
        this.afterVersionId = afterVersionId;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
