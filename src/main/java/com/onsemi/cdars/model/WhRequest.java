package com.onsemi.cdars.model;

public class WhRequest {

    private String id;
    private String inventoryId;
    private String requestType;
    private String equipmentType;
    private String pcbType;
     private String pcbLimitId;
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
    private String mpNo;
    private String mpExpiryDate;
    private String location;
    private String rack;
    private String shelf;
    private String requestedBy;
    private String requestorEmail;
    private String requestedDate;
    private String finalApprovedStatus;
    private String finalApprovedBy;
    private String finalApprovedDate;
    private String remarks;
    private String remarksApprover;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String status;
    private String flag;

    /*Extra*/
    private String selected;
    private String requestedDateView;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
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

    public String getFinalApprovedBy() {
        return finalApprovedBy;
    }

    public void setFinalApprovedBy(String finalApprovedBy) {
        this.finalApprovedBy = finalApprovedBy;
    }

    public String getFinalApprovedDate() {
        return finalApprovedDate;
    }

    public void setFinalApprovedDate(String finalApprovedDate) {
        this.finalApprovedDate = finalApprovedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getRequestedDateView() {
        return requestedDateView;
    }

    public void setRequestedDateView(String requestedDateView) {
        this.requestedDateView = requestedDateView;
    }

    public String getFinalApprovedStatus() {
        return finalApprovedStatus;
    }

    public void setFinalApprovedStatus(String finalApprovedStatus) {
        this.finalApprovedStatus = finalApprovedStatus;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemarksApprover() {
        return remarksApprover;
    }

    public void setRemarksApprover(String remarksApprover) {
        this.remarksApprover = remarksApprover;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequestorEmail() {
        return requestorEmail;
    }

    public void setRequestorEmail(String requestorEmail) {
        this.requestorEmail = requestorEmail;
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

    public String getPcbType() {
        return pcbType;
    }

    public void setPcbType(String pcbType) {
        this.pcbType = pcbType;
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

    public String getPcbLimitId() {
        return pcbLimitId;
    }

    public void setPcbLimitId(String pcbLimitId) {
        this.pcbLimitId = pcbLimitId;
    }
    
    

}
