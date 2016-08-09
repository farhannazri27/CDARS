package com.onsemi.cdars.model;

public class PcbFtpTemp {

    private String dateOff;
    private String rms;
    private String process;
    private String status;
    private String supportItem;
    private String createdDate;

    public PcbFtpTemp() {

    }

    public PcbFtpTemp(String dateOff,
            String rms,
            String process,
            String status,
            String supportItem) {

        super();
        this.dateOff = dateOff;
        this.rms = rms;
        this.process = process;
        this.status = status;
        this.supportItem = supportItem;
    }

    public String getDateOff() {
        return dateOff;
    }

    public void setDateOff(String dateOff) {
        this.dateOff = dateOff;
    }

    public String getRms() {
        return rms;
    }

    public void setRms(String rms) {
        this.rms = rms;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupportItem() {
        return supportItem;
    }

    public void setSupportItem(String supportItem) {
        this.supportItem = supportItem;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
