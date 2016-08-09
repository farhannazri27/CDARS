/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.cdars.model;

/**
 *
 * @author fg79cj
 */
public class IonicFtpTemp {
    
    private String eventCode;
    private String rms;
    private String intervals;
    private String currentStatus;
    private String dateOff;
    private String equipId;
    private String lcode;
    private String hardwareFinal;
    private String supportItem;
    private String createdDate;

    public IonicFtpTemp() {
    }

    public IonicFtpTemp(String eventCode, String rms,
            String intervals, String currentStatus,
            String dateOff, String equipId,
            String lcode, String hardwareFinal, String supportItem) {
        super();
        this.eventCode = eventCode;
        this.rms = rms;
        this.intervals = intervals;
        this.currentStatus = currentStatus;
        this.dateOff = dateOff;
        this.equipId = equipId;
        this.lcode = lcode;
        this.hardwareFinal = hardwareFinal;
        this.supportItem = supportItem;
    }

    @Override
    public String toString() {
        return "ionicFtp [eventCode=" + eventCode + ", rms=" + rms
                + ", intervals=" + intervals + ", currentStatus=" + currentStatus + ","
                + "dateOff=" + dateOff + ", equipId=" + equipId
                + ", lcode=" + lcode + ", hardwareFinal=" + hardwareFinal + ", supportItem=" + supportItem + "]";
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getRms() {
        return rms;
    }

    public void setRms(String rms) {
        this.rms = rms;
    }

    public String getIntervals() {
        return intervals;
    }

    public void setIntervals(String intervals) {
        this.intervals = intervals;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDateOff() {
        return dateOff;
    }

    public void setDateOff(String dateOff) {
        this.dateOff = dateOff;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public String getLcode() {
        return lcode;
    }

    public void setLcode(String lcode) {
        this.lcode = lcode;
    }

    public String getHardwareFinal() {
        return hardwareFinal;
    }

    public void setHardwareFinal(String hardwareFinal) {
        this.hardwareFinal = hardwareFinal;
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
