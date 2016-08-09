package com.onsemi.cdars.model;

public class UserGroupAccess {
    /* User Group Access Base */
    private String id;
    private String groupId;
    private String menuId;

    public UserGroupAccess(String id, String groupId, String menuId) {
        this.id = id;
        this.groupId = groupId;
        this.menuId = menuId;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    /* Menu */
    private String parentCode;
    private String code;
    private String name;
    private String selected;

    public UserGroupAccess(String id, String groupId, String menuId, String parentCode, String code, String name, String selected) {
        this.id = id;
        this.groupId = groupId;
        this.menuId = menuId;
        this.parentCode = parentCode;
        this.code = code;
        this.name = name;
        this.selected = selected;
    }
    
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
