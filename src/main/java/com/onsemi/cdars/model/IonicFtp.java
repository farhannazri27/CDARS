package com.onsemi.cdars.model;

public class IonicFtp {

    private String id;
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

    public IonicFtp() {
    }

    public IonicFtp(String eventCode, String rms,
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
        return "ionicFtp [id=" + id + ",eventCode=" + eventCode + ", rms=" + rms
                + ", intervals=" + intervals + ", currentStatus=" + currentStatus + ","
                + "dateOff=" + dateOff + ", equipId=" + equipId
                + ", lcode=" + lcode + ", hardwareFinal=" + hardwareFinal + ", supportItem=" + supportItem + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
