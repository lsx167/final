package com.entities;

public class PagePO {
    private long id;
    //页面名称
    private String name;
    //页面创始人id
    private long originatorID;
    //页面权限类型：1、所有人可读可写，2、所有人可读部分人可写，
    // 3、部分人可读部分人可写，4、私密
    private Integer type;
    //可读人id集
    private String readID;
    //可写人id集
    private String writeID;
    //所属空间id
    private long spaceID;
    //是否为根页面(1、是，2、否)
    private boolean isRootPage;
    //页面深度
    private Integer depth;
    //父页面id（不是根页面才有父页面）
    private long fatherPageID;
    //子页面id集
    private String childPageID;
    //版本号
    private String versionID;
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

    public long getSpaceID() {
        return spaceID;
    }

    public void setSpaceID(long spaceID) {
        this.spaceID = spaceID;
    }

    public boolean isRootPage() {
        return isRootPage;
    }

    public void setRootPage(boolean rootPage) {
        isRootPage = rootPage;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public long getFatherPageID() {
        return fatherPageID;
    }

    public void setFatherPageID(long fatherPageID) {
        this.fatherPageID = fatherPageID;
    }

    public String getChildPageID() {
        return childPageID;
    }

    public void setChildPageID(String childPageID) {
        this.childPageID = childPageID;
    }

    public String getVersionID() {
        return versionID;
    }

    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
