package com.entities;

import java.text.SimpleDateFormat;
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
    private String operatorTime;
    //操作类型（1、创建空间，2、更改权限，3、新建页面，4、修改页面，5、删除页面）
    private Integer type;
    //操作内容
    private String operatorContent;

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

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
