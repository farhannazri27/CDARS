package com.onsemi.cdars.model;

public class User {
    /* User */
    private String id;
    private String loginId;
    private String password;
    private String groupId;
    private String isActive;
    private String createdBy;
    private String createdTime;
    private String modifiedBy;
    private String modifiedTime;

    public User() {
    }

    public User(String id, String loginId, String password, String groupId, String isActive, String createdBy, String createdTime, String modifiedBy, String modifiedTime) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.groupId = groupId;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modifiedBy = modifiedBy;
        this.modifiedTime = modifiedTime;
    }

    public User(String id, String loginId, String password, String groupId, String isActive, String createdBy, String createdTime, String modifiedBy, String modifiedTime, String groupCode, String groupName, String fullname, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.groupId = groupId;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modifiedBy = modifiedBy;
        this.modifiedTime = modifiedTime;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.fullname = fullname;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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
    
    /* User Group */
    private String groupCode;
    private String groupName;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    
    /* User Profile */
    private String fullname;
    private String email;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
