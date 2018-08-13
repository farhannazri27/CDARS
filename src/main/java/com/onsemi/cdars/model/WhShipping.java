package com.onsemi.cdars.model;

public class WhShipping {

    private String id;
    private String requestId;
    private String boxId;
    private String materialPass;
    private String hardwareBarcode1;
    private String dateScan1;
    private String hardwareBarcode2;
    private String dateScan2;
    private String shippingDate;
    private String status;
    private String remarks;
    private String flag;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String mpNo;
    private String mpExpiryDate;
    private String mpCreatedDate;

    //to be merge with whRequest
    private String requestEquipmentType;
    private String requestEquipmentId;
    private String requestQuantity;
    private String requestType;
    private String requestRequestedBy;
    private String requestRequestorEmail;
    private String requestRequestedDate;
    private String requestViewRequestedDate;
    private String pcbA;
    private String pcbAQty;
    private String pcbB;
    private String pcbBQty;
    private String pcbC;
    private String pcbCQty;
    private String pcbCtr;
    private String pcbCtrQty;
    private String count;

    //spts SFPkid
    private String sfpkid;
    private String sfpkidB;
    private String sfpkidC;
    private String sfpkidCtr;
    private String itempkid;

    //phase 2
    private String loadCard;
    private String loadCardQty;
    private String programCard;
    private String programCardQty;
    private String sfpkidLc;
    private String sfpkidPc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getMaterialPass() {
        return materialPass;
    }

    public void setMaterialPass(String materialPass) {
        this.materialPass = materialPass;
    }

    public String getHardwareBarcode1() {
        return hardwareBarcode1;
    }

    public void setHardwareBarcode1(String hardwareBarcode1) {
        this.hardwareBarcode1 = hardwareBarcode1;
    }

    public String getDateScan1() {
        return dateScan1;
    }

    public void setDateScan1(String dateScan1) {
        this.dateScan1 = dateScan1;
    }

    public String getHardwareBarcode2() {
        return hardwareBarcode2;
    }

    public void setHardwareBarcode2(String hardwareBarcode2) {
        this.hardwareBarcode2 = hardwareBarcode2;
    }

    public String getDateScan2() {
        return dateScan2;
    }

    public void setDateScan2(String dateScan2) {
        this.dateScan2 = dateScan2;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(String requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
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

    public String getRequestRequestedDate() {
        return requestRequestedDate;
    }

    public void setRequestRequestedDate(String requestRequestedDate) {
        this.requestRequestedDate = requestRequestedDate;
    }

    public String getRequestViewRequestedDate() {
        return requestViewRequestedDate;
    }

    public void setRequestViewRequestedDate(String requestViewRequestedDate) {
        this.requestViewRequestedDate = requestViewRequestedDate;
    }

    public String getRequestRequestorEmail() {
        return requestRequestorEmail;
    }

    public void setRequestRequestorEmail(String requestRequestorEmail) {
        this.requestRequestorEmail = requestRequestorEmail;
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

    public String getMpCreatedDate() {
        return mpCreatedDate;
    }

    public void setMpCreatedDate(String mpCreatedDate) {
        this.mpCreatedDate = mpCreatedDate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSfpkid() {
        return sfpkid;
    }

    public void setSfpkid(String sfpkid) {
        this.sfpkid = sfpkid;
    }

    public String getItempkid() {
        return itempkid;
    }

    public void setItempkid(String itempkid) {
        this.itempkid = itempkid;
    }

    public String getSfpkidB() {
        return sfpkidB;
    }

    public void setSfpkidB(String sfpkidB) {
        this.sfpkidB = sfpkidB;
    }

    public String getSfpkidC() {
        return sfpkidC;
    }

    public void setSfpkidC(String sfpkidC) {
        this.sfpkidC = sfpkidC;
    }

    public String getSfpkidCtr() {
        return sfpkidCtr;
    }

    public void setSfpkidCtr(String sfpkidCtr) {
        this.sfpkidCtr = sfpkidCtr;
    }

    public String getLoadCard() {
        return loadCard;
    }

    public void setLoadCard(String loadCard) {
        this.loadCard = loadCard;
    }

    public String getLoadCardQty() {
        return loadCardQty;
    }

    public void setLoadCardQty(String loadCardQty) {
        this.loadCardQty = loadCardQty;
    }

    public String getProgramCard() {
        return programCard;
    }

    public void setProgramCard(String programCard) {
        this.programCard = programCard;
    }

    public String getProgramCardQty() {
        return programCardQty;
    }

    public void setProgramCardQty(String programCardQty) {
        this.programCardQty = programCardQty;
    }

    public String getSfpkidLc() {
        return sfpkidLc;
    }

    public void setSfpkidLc(String sfpkidLc) {
        this.sfpkidLc = sfpkidLc;
    }

    public String getSfpkidPc() {
        return sfpkidPc;
    }

    public void setSfpkidPc(String sfpkidPc) {
        this.sfpkidPc = sfpkidPc;
    }

}
