package com.onsemi.cdars.model;

public class WhRetrieval {

    private String id;
    private String requestId;
    private String hardwareType;
    private String hardwareId;
    private String pcbA;
    private String pcbAQty;
    private String pcbB;
    private String pcbBQty;
    private String pcbC;
    private String pcbCQty;
    private String pcbCtr;
    private String pcbCtrQty;
    private String hardwareQty;
    private String mpNo;
    private String mpExpiryDate;
    private String location;
    private String shelf;
    private String rack;
    private String requestedBy;
    private String requestedDate;
    private String verifiedBy;
    private String verifiedDate;
    private String shippingBy;
    private String shippingDate;
    private String receivedDate;
    private String remarks;
    private String ttVerification;
    private String ttVerifiedBy;
    private String ttVerifiedDate;
    private String barcodeVerification;
    private String barcodeVerifiedBy;
    private String barcodeVerifiedDate;
    private String status;
    private String flag;
    private String barcodeDisposition;
    private String barcodeDispositionBy;
    private String barcodeDispositionRemarks;
    private String barcodeDispositionDate;
    private String ttDisposition;
    private String ttDispositionBy;
    private String ttDispositionRemarks;
    private String ttDispositionDate;

    //date view
    private String viewRequestedDate;
    private String viewMpExpiryDate;
    private String viewVerifiedDate;
    private String viewShippingDate;
    private String viewReceivedDate;
    private String viewTtVerifiedDate;
    private String viewBarcodeVerifiedDate;
    private String viewBarcodeDispositionDate;
    private String viewTtDispositionDate;

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

    public String getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(String hardwareType) {
        this.hardwareType = hardwareType;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getHardwareQty() {
        return hardwareQty;
    }

    public void setHardwareQty(String hardwareQty) {
        this.hardwareQty = hardwareQty;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
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

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(String verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getShippingBy() {
        return shippingBy;
    }

    public void setShippingBy(String shippingBy) {
        this.shippingBy = shippingBy;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getTtVerification() {
        return ttVerification;
    }

    public void setTtVerification(String ttVerification) {
        this.ttVerification = ttVerification;
    }

    public String getTtVerifiedBy() {
        return ttVerifiedBy;
    }

    public void setTtVerifiedBy(String ttVerifiedBy) {
        this.ttVerifiedBy = ttVerifiedBy;
    }

    public String getTtVerifiedDate() {
        return ttVerifiedDate;
    }

    public void setTtVerifiedDate(String ttVerifiedDate) {
        this.ttVerifiedDate = ttVerifiedDate;
    }

    public String getBarcodeVerification() {
        return barcodeVerification;
    }

    public void setBarcodeVerification(String barcodeVerification) {
        this.barcodeVerification = barcodeVerification;
    }

    public String getBarcodeVerifiedBy() {
        return barcodeVerifiedBy;
    }

    public void setBarcodeVerifiedBy(String barcodeVerifiedBy) {
        this.barcodeVerifiedBy = barcodeVerifiedBy;
    }

    public String getBarcodeVerifiedDate() {
        return barcodeVerifiedDate;
    }

    public void setBarcodeVerifiedDate(String barcodeVerifiedDate) {
        this.barcodeVerifiedDate = barcodeVerifiedDate;
    }

    public String getViewRequestedDate() {
        return viewRequestedDate;
    }

    public void setViewRequestedDate(String viewRequestedDate) {
        this.viewRequestedDate = viewRequestedDate;
    }

    public String getViewMpExpiryDate() {
        return viewMpExpiryDate;
    }

    public void setViewMpExpiryDate(String viewMpExpiryDate) {
        this.viewMpExpiryDate = viewMpExpiryDate;
    }

    public String getViewVerifiedDate() {
        return viewVerifiedDate;
    }

    public void setViewVerifiedDate(String viewVerifiedDate) {
        this.viewVerifiedDate = viewVerifiedDate;
    }

    public String getViewShippingDate() {
        return viewShippingDate;
    }

    public void setViewShippingDate(String viewShippingDate) {
        this.viewShippingDate = viewShippingDate;
    }

    public String getViewReceivedDate() {
        return viewReceivedDate;
    }

    public void setViewReceivedDate(String viewReceivedDate) {
        this.viewReceivedDate = viewReceivedDate;
    }

    public String getViewTtVerifiedDate() {
        return viewTtVerifiedDate;
    }

    public void setViewTtVerifiedDate(String viewTtVerifiedDate) {
        this.viewTtVerifiedDate = viewTtVerifiedDate;
    }

    public String getViewBarcodeVerifiedDate() {
        return viewBarcodeVerifiedDate;
    }

    public void setViewBarcodeVerifiedDate(String viewBarcodeVerifiedDate) {
        this.viewBarcodeVerifiedDate = viewBarcodeVerifiedDate;
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

    public String getBarcodeDisposition() {
        return barcodeDisposition;
    }

    public void setBarcodeDisposition(String barcodeDisposition) {
        this.barcodeDisposition = barcodeDisposition;
    }

    public String getBarcodeDispositionBy() {
        return barcodeDispositionBy;
    }

    public void setBarcodeDispositionBy(String barcodeDispositionBy) {
        this.barcodeDispositionBy = barcodeDispositionBy;
    }

    public String getBarcodeDispositionRemarks() {
        return barcodeDispositionRemarks;
    }

    public void setBarcodeDispositionRemarks(String barcodeDispositionRemarks) {
        this.barcodeDispositionRemarks = barcodeDispositionRemarks;
    }

    public String getBarcodeDispositionDate() {
        return barcodeDispositionDate;
    }

    public void setBarcodeDispositionDate(String barcodeDispositionDate) {
        this.barcodeDispositionDate = barcodeDispositionDate;
    }

    public String getTtDisposition() {
        return ttDisposition;
    }

    public void setTtDisposition(String ttDisposition) {
        this.ttDisposition = ttDisposition;
    }

    public String getTtDispositionBy() {
        return ttDispositionBy;
    }

    public void setTtDispositionBy(String ttDispositionBy) {
        this.ttDispositionBy = ttDispositionBy;
    }

    public String getTtDispositionRemarks() {
        return ttDispositionRemarks;
    }

    public void setTtDispositionRemarks(String ttDispositionRemarks) {
        this.ttDispositionRemarks = ttDispositionRemarks;
    }

    public String getTtDispositionDate() {
        return ttDispositionDate;
    }

    public void setTtDispositionDate(String ttDispositionDate) {
        this.ttDispositionDate = ttDispositionDate;
    }

    public String getViewBarcodeDispositionDate() {
        return viewBarcodeDispositionDate;
    }

    public void setViewBarcodeDispositionDate(String viewBarcodeDispositionDate) {
        this.viewBarcodeDispositionDate = viewBarcodeDispositionDate;
    }

    public String getViewTtDispositionDate() {
        return viewTtDispositionDate;
    }

    public void setViewTtDispositionDate(String viewTtDispositionDate) {
        this.viewTtDispositionDate = viewTtDispositionDate;
    }

}
