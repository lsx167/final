package com.entities;

//空间实体
public class SpacePO {
    private long id;
    //空间名称
    private String name;
    //空间创始人id
    private long originatorID;
    //空间权限类型：1、所有人可读可写，2、所有人可读部分人可写，
    // 3、部分人可读部分人可写，4、私密
    private Integer type;
    //可读人id集
    private String readID;
    //可写人id集
    private String writeID;
    //子页面id集
    private String childPageID;
    private boolean expired;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOriginatorID() {
        return originatorID;
    }

    public void setOriginatorID(long originatorID) {
        this.originatorID = originatorID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReadID() {
        return readID;
    }

    public void setReadID(String readID) {
        this.readID = readID;
    }

    public String getWriteID() {
        return writeID;
    }

    public void setWriteID(String writeID) {
        this.writeID = writeID;
    }

    public String getChildPageID() {
        return childPageID;
    }

    public void setChildPageID(String childPageID) {
        this.childPageID = childPageID;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}