package com.onsemi.cdars.model;

public class EmailConfig {

    private String id;
    private String taskPdetailsCode;
    private String userOncid;
    private String userName;
    private String email;
    private String remarks;
    private String flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskPdetailsCode() {
        return taskPdetailsCode;
    }

    public void setTaskPdetailsCode(String taskPdetailsCode) {
        this.taskPdetailsCode = taskPdetailsCode;
    }

    public String getUserOncid() {
        return userOncid;
    }

    public void setUserOncid(String userOncid) {
        this.userOncid = userOncid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
