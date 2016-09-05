package com.onsemi.cdars.model;

public class WhInventoryTemp {

    private String inventoryId;
    private String mpNo;
    private String mpExpiryDate;
    private String equipmentType;
    private String equipmentId;
    private String quantity;
    private String requestedBy;
    private String requestedDate;
    private String remarks;
    private String verifiedDate;
    private String inventoryDate;
    private String invetoryRack;
    private String inventorySlot;
    private String inventoryBy;
    private String status;

    public WhInventoryTemp(String inventoryId,
            String mpNo,
            String mpExpiryDate,
            String equipmentType,
            String equipmentId,
            String quantity,
            String requestedBy,
            String requestedDate,
            String remarks,
            String verifiedDate,
            String inventoryDate,
            String invetoryRack,
            String inventorySlot,
            String inventoryBy,
            String status) {

        super();
        this.inventoryId = inventoryId;
        this.mpNo = mpNo;
        this.mpExpiryDate = mpExpiryDate;
        this.equipmentType = equipmentType;
        this.equipmentId = equipmentId;
        this.quantity = quantity;
        this.requestedBy = requestedBy;
        this.requestedDate = requestedDate;
        this.remarks = remarks;
        this.verifiedDate = verifiedDate;
        this.inventoryDate = inventoryDate;
        this.invetoryRack = invetoryRack;
        this.inventorySlot = inventorySlot;
        this.inventoryBy = inventoryBy;
        this.status = status;

    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
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

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(String verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public String getInvetoryRack() {
        return invetoryRack;
    }

    public void setInvetoryRack(String invetoryRack) {
        this.invetoryRack = invetoryRack;
    }

    public String getInventorySlot() {
        return inventorySlot;
    }

    public void setInventorySlot(String inventorySlot) {
        this.inventorySlot = inventorySlot;
    }

    public String getInventoryBy() {
        return inventoryBy;
    }

    public void setInventoryBy(String inventoryBy) {
        this.inventoryBy = inventoryBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
