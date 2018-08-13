package com.onsemi.cdars.model;

public class EmailTimelapse {

    private String id;
    private String sendCc;
    private String userOncid;
    private String userName;
    private String email;
    private String flag;
    private String remarks;
    private String select;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendCc() {
        return sendCc;
    }

    public void setSendCc(String sendCc) {
        this.sendCc = sendCc;
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
