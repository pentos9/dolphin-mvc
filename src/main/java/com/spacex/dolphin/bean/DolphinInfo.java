package com.spacex.dolphin.bean;

public class DolphinInfo {
    private String name;
    private String type;
    private String remark;

    private static final DolphinInfo defaultDolphinInfo = new DolphinInfo("dolphin", "framework", "hook");

    public DolphinInfo() {
    }

    public DolphinInfo(String name, String type, String remark) {
        this.name = name;
        this.type = type;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static DolphinInfo getDefault() {
        return defaultDolphinInfo;
    }
}
