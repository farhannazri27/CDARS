package com.onsemi.cdars.model;

public class WhStatusLog {

    private String id;
    private String requestId;
    private String module;
    private String status;
    private String statusDate;
    private String createdBy;
    private String flag;
    private String count;

    
//    for timelapse ship
    private String requestToApprove;
    private String approveToMPCreated;
    private String mpCreatedToTtScan;
    private String ttScanToBsScan;
    private String bsScanToShip;
    private String shipToInventory;
    
        
//    for timelapse retrieve
    private String requestToVerifiedDate; //received date for SF
    private String verifiedDatetoShipDate; //SF shipping date
    private String shipDateToBsScan;
    private String bsScanToTtScan;
    
    private String requestToTtScan; //whole process for retrieval
    private String requestToInventory;  //whole process for shipment
    
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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRequestToApprove() {
        return requestToApprove;
    }

    public void setRequestToApprove(String requestToApprove) {
        this.requestToApprove = requestToApprove;
    }

    public String getApproveToMPCreated() {
        return approveToMPCreated;
    }

    public void setApproveToMPCreated(String approveToMPCreated) {
        this.approveToMPCreated = approveToMPCreated;
    }

    public String getMpCreatedToTtScan() {
        return mpCreatedToTtScan;
    }

    public void setMpCreatedToTtScan(String mpCreatedToTtScan) {
        this.mpCreatedToTtScan = mpCreatedToTtScan;
    }

    public String getTtScanToBsScan() {
        return ttScanToBsScan;
    }

    public void setTtScanToBsScan(String ttScanToBsScan) {
        this.ttScanToBsScan = ttScanToBsScan;
    }

    public String getBsScanToShip() {
        return bsScanToShip;
    }

    public void setBsScanToShip(String bsScanToShip) {
        this.bsScanToShip = bsScanToShip;
    }

    public String getShipToInventory() {
        return shipToInventory;
    }

    public void setShipToInventory(String shipToInventory) {
        this.shipToInventory = shipToInventory;
    }

    public String getRequestToVerifiedDate() {
        return requestToVerifiedDate;
    }

    public void setRequestToVerifiedDate(String requestToVerifiedDate) {
        this.requestToVerifiedDate = requestToVerifiedDate;
    }

    public String getVerifiedDatetoShipDate() {
        return verifiedDatetoShipDate;
    }

    public void setVerifiedDatetoShipDate(String verifiedDatetoShipDate) {
        this.verifiedDatetoShipDate = verifiedDatetoShipDate;
    }

    public String getShipDateToBsScan() {
        return shipDateToBsScan;
    }

    public void setShipDateToBsScan(String shipDateToBsScan) {
        this.shipDateToBsScan = shipDateToBsScan;
    }

    public String getBsScanToTtScan() {
        return bsScanToTtScan;
    }

    public void setBsScanToTtScan(String bsScanToTtScan) {
        this.bsScanToTtScan = bsScanToTtScan;
    }

    public String getRequestToTtScan() {
        return requestToTtScan;
    }

    public void setRequestToTtScan(String requestToTtScan) {
        this.requestToTtScan = requestToTtScan;
    }

    public String getRequestToInventory() {
        return requestToInventory;
    }

    public void setRequestToInventory(String requestToInventory) {
        this.requestToInventory = requestToInventory;
    }
    
    

}
