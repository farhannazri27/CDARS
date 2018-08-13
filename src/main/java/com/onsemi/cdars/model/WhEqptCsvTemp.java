package com.onsemi.cdars.model;

public class WhEqptCsvTemp {

    private String equipmentType;
    private String equipmentId;
    private String quantity;
    private String mpNo;
    private String mpExpiryDate;
    private String requestedBy;
    private String requestedEmail;
    private String rack;
    private String shelf;

    public WhEqptCsvTemp(String equipmentType,
            String equipmentId,
            String quantity,
            String mpNo,
            String mpExpiryDate,
            String requestedBy,
            String requestedEmail,
            String rack,
            String shelf) {

        super();

        this.equipmentType = equipmentType;
        this.equipmentId = equipmentId;
        this.quantity = quantity;
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

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

}
