package com.onsemi.cdars.model;

public class UserGroup {
    private String id;
    private String code;
    private String name;
    private String createdBy;
    private String createdTime;
    private String modifiedBy;
    private String modifiedTime;
    private String selected;

    public UserGroup() {
    }

    public UserGroup(String id, String code, String name, String selected) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.selected = selected;
    }
    
    public UserGroup(String id, String code, String name, String createdBy, String createdTime, String modifiedBy, String modifiedTime) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modifiedBy = modifiedBy;
        this.modifiedTime = modifiedTime;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
    
}
