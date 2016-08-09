package com.onsemi.cdars.model;

public class UserSession {
    
    private String id;
    private String loginId;
    private String fullname;
    private String email;
    private String group;
    private String[] programAccess;

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String[] getProgramAccess() {
        return programAccess;
    }

    public void setProgramAccess(String[] programAccess) {
        this.programAccess = programAccess;
    }
    
}
