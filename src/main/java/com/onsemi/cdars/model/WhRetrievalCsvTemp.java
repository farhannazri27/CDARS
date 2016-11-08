package com.onsemi.cdars.model;

public class WhRetrievalCsvTemp {

    private String requestId;
    private String requestEquipmentType;
    private String requestEquipmentId;
    private String retrievalReason;
    private String pcbA;
    private String pcbAQty;
    private String pcbB;
    private String pcbBQty;
    private String pcbC;
    private String pcbCQty;
    private String pcbCtr;
    private String pcbCtrQty;
    private String requestQuantity;
    private String mpNo;
    private String mpExpiryDate;
    private String rack;
    private String shelf;
    private String requestRequestedBy;
    private String requestRequestorEmail;
    private String requestRequestedDate;
    private String Remarks;
    private String status;

    public WhRetrievalCsvTemp(String requestId,
            String requestEquipmentType,
            String requestEquipmentId,
            String retrievalReason,
            String pcbA,
            String pcbAQty,
            String pcbB,
            String pcbBQty,
            String pcbC,
            String pcbCQty,
            String pcbCtr,
            String pcbCtrQty,
            String requestQuantity,
            String mpNo,
            String mpExpiryDate,
            String rack,
            String shelf,
            String requestRequestedBy,
            String requestRequestorEmail,
            String requestRequestedDate,
            String Remarks,
            String status) {

        super();
        this.requestId = requestId;
        this.requestEquipmentType = requestEquipmentType;
        this.requestEquipmentId = requestEquipmentId;
        this.retrievalReason = retrievalReason;
        this.pcbA = pcbA;
        this.pcbAQty = pcbAQty;
        this.pcbB = pcbB;
        this.pcbBQty = pcbBQty;
        this.pcbC = pcbC;
        this.pcbCQty = pcbCQty;
        this.pcbCtr = pcbCtr;
        this.pcbCtrQty = pcbCtrQty;
        this.requestQuantity = requestQuantity;
        this.mpNo = mpNo;
        this.mpExpiryDate = mpExpiryDate;
        this.rack = rack;
        this.shelf = shelf;
        this.requestRequestedBy = requestRequestedBy;
        this.requestRequestorEmail = requestRequestorEmail;
        this.requestRequestedDate = requestRequestedDate;
        this.Remarks = Remarks;
        this.status = status;

    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestEquipmentType() {
        return requestEquipmentType;
    }

    public void setRequestEquipmentType(String requestEquipmentType) {
        this.requestEquipmentType = requestEquipmentType;
    }

    public String getRequestEquipmentId() {
        return requestEquipmentId;
    }

    public void setRequestEquipmentId(String requestEquipmentId) {
        this.requestEquipmentId = requestEquipmentId;
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

    public String getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(String requestQuantity) {
        this.requestQuantity = requestQuantity;
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

    public String getRequestRequestedBy() {
        return requestRequestedBy;
    }

    public void setRequestRequestedBy(String requestRequestedBy) {
        this.requestRequestedBy = requestRequestedBy;
    }

    public String getRequestRequestorEmail() {
        return requestRequestorEmail;
    }

    public void setRequestRequestorEmail(String requestRequestorEmail) {
        this.requestRequestorEmail = requestRequestorEmail;
    }

    public String getRequestRequestedDate() {
        return requestRequestedDate;
    }

    public void setRequestRequestedDate(String requestRequestedDate) {
        this.requestRequestedDate = requestRequestedDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRetrievalReason() {
        return retrievalReason;
    }

    public void setRetrievalReason(String retrievalReason) {
        this.retrievalReason = retrievalReason;
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
