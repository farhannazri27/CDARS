package com.onsemi.cdars.model;

public class WhInventoryTemp {

    private String requestId;
    private String mpNo;
    private String mpExpiryDate;
    private String equipmentType;
    private String equipmentId;
    private String pcbA;
    private String pcbAQty;
    private String pcbB;
    private String pcbBQty;
    private String pcbC;
    private String pcbCQty;
    private String pcbCtr;
    private String pcbCtrQty;
    private String quantity;
    private String requestedBy;
    private String requestedDate;
    private String remarks;
    private String verifiedDate;
    private String receivalDate;
    private String inventoryDate;
    private String inventoryRack;
    private String inventoryShelf;
    private String inventoryBy;
    private String status;

    public WhInventoryTemp(String requestId,
            String mpNo,
            String mpExpiryDate,
            String equipmentType,
            String equipmentId,
            String pcbA,
            String pcbAQty,
            String pcbB,
            String pcbBQty,
            String pcbC,
            String pcbCQty,
            String pcbCtr,
            String pcbCtrQty,
            String quantity,
            String requestedBy,
            String requestedDate,
            String remarks,
            String verifiedDate,
            String receivalDate,
            String inventoryDate,
            String inventoryRack,
            String inventoryShelf,
            String inventoryBy,
            String status) {

        super();
        this.requestId = requestId;
        this.mpNo = mpNo;
        this.mpExpiryDate = mpExpiryDate;
        this.equipmentType = equipmentType;
        this.equipmentId = equipmentId;
        this.pcbA = pcbA;
        this.pcbAQty = pcbAQty;
        this.pcbB = pcbB;
        this.pcbBQty = pcbBQty;
        this.pcbC = pcbC;
        this.pcbCQty = pcbCQty;
        this.pcbCtr = pcbCtr;
        this.pcbCtrQty = pcbCtrQty;
        this.quantity = quantity;
        this.requestedBy = requestedBy;
        this.requestedDate = requestedDate;
        this.remarks = remarks;
        this.verifiedDate = verifiedDate;
        this.receivalDate = receivalDate;
        this.inventoryDate = inventoryDate;
        this.inventoryRack = inventoryRack;
        this.inventoryShelf = inventoryShelf;
        this.inventoryBy = inventoryBy;
        this.status = status;

    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getInventoryRack() {
        return inventoryRack;
    }

    public void setInventoryRack(String inventoryRack) {
        this.inventoryRack = inventoryRack;
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

    public String getPcbA() {
        return pcbA;
    }

    public void setPcbA(String pcbA) {
        this.pcbA = pcbA;
    }

    public String getPcbAQty() {
        return pcbAQty;
    }

    public void setPcbAQty(String pcbAQty) {
        this.pcbAQty = pcbAQty;
    }

    public String getPcbB() {
        return pcbB;
    }

    public void setPcbB(String pcbB) {
        this.pcbB = pcbB;
    }

    public String getPcbBQty() {
        return pcbBQty;
    }

    public void setPcbBQty(String pcbBQty) {
        this.pcbBQty = pcbBQty;
    }

    public String getPcbC() {
        return pcbC;
    }

    public void setPcbC(String pcbC) {
        this.pcbC = pcbC;
    }

    public String getPcbCQty() {
        return pcbCQty;
    }

    public void setPcbCQty(String pcbCQty) {
        this.pcbCQty = pcbCQty;
    }

    public String getPcbCtr() {
        return pcbCtr;
    }

    public void setPcbCtr(String pcbCtr) {
        this.pcbCtr = pcbCtr;
    }

    public String getPcbCtrQty() {
        return pcbCtrQty;
    }

    public void setPcbCtrQty(String pcbCtrQty) {
        this.pcbCtrQty = pcbCtrQty;
    }

    public String getInventoryShelf() {
        return inventoryShelf;
    }

    public void setInventoryShelf(String inventoryShelf) {
        this.inventoryShelf = inventoryShelf;
    }

    public String getReceivalDate() {
        return receivalDate;
    }

    public void setReceivalDate(String receivalDate) {
        this.receivalDate = receivalDate;
    }

}
