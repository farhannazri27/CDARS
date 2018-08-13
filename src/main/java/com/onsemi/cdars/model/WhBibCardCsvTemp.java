package com.onsemi.cdars.model;

public class WhBibCardCsvTemp {

    private String equipmentType;
    private String pairSingle;
    private String loadCardId;
    private String loadCardQty;
    private String programCardId;
    private String programCardQty;
    private String totalQty;
    private String mpNo;
    private String mpExpiryDate;
    private String requestedBy;
    private String requestedEmail;
    private String rack;
    private String shelf;

    public WhBibCardCsvTemp(
            String equipmentType,
            String pairSingle,
            String loadCardId,
            String loadCardQty,
            String programCardId,
            String programCardQty,
            String totalQty,
            String mpNo,
            String mpExpiryDate,
            String requestedBy,
            String requestedEmail,
            String rack,
            String shelf) {

        super();

        this.equipmentType = equipmentType;
        this.pairSingle = pairSingle;
        this.loadCardId = loadCardId;
        this.loadCardQty = loadCardQty;
        this.programCardId = programCardId;
        this.programCardQty = programCardQty;
        this.totalQty = totalQty;
        this.mpNo = mpNo;
        this.mpExpiryDate = mpExpiryDate;
        this.requestedBy = requestedBy;
        this.requestedEmail = requestedEmail;
        this.rack = rack;
        this.shelf = shelf;

    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getMpExpiryDate() {
        return mpExpiryDate;
    }

    public void setMpExpiryDate(String mpExpiryDate) {
        this.mpExpiryDate = mpExpiryDate;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getRequestedEmail() {
        return requestedEmail;
    }

    public void setRequestedEmail(String requestedEmail) {
        this.requestedEmail = requestedEmail;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getPairSingle() {
        return pairSingle;
    }

    public void setPairSingle(String pairSingle) {
        this.pairSingle = pairSingle;
    }

    public String getLoadCardId() {
        return loadCardId;
    }

    public void setLoadCardId(String loadCardId) {
        this.loadCardId = loadCardId;
    }

    public String getLoadCardQty() {
        return loadCardQty;
    }

    public void setLoadCardQty(String loadCardQty) {
        this.loadCardQty = loadCardQty;
    }

    public String getProgramCardId() {
        return programCardId;
    }

    public void setProgramCardId(String programCardId) {
        this.programCardId = programCardId;
    }

    public String getProgramCardQty() {
        return programCardQty;
    }

    public void setProgramCardQty(String programCardQty) {
        this.programCardQty = programCardQty;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

}
