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
    private String equipmentType;
    private String equipmentId;
    private String mpNo;
    private String loadCard;
    private String programCard;

//    for timelapse ship
    private String requestToApprove;
    private String approveToMPCreated;
    private String mpCreatedToTtScan;
    private String ttScanToBsScan;
    private String bsScanToShip;
    private String shipToInventory;

    private String requestToApproveTemp;
    private String approveToMPCreatedTemp;
    private String mpCreatedToTtScanTemp;
    private String ttScanToBsScanTemp;
    private String bsScanToShipTemp;
    private String shipToInventoryTemp;

    private String requestToApprove24;
    private String approveToMPCreated24;
    private String mpCreatedToTtScan24;
    private String ttScanToBsScan24;
    private String bsScanToShip24;
    private String shipToInventory24;

    private String requestToApproveTemp24;
    private String approveToMPCreatedTemp24;
    private String mpCreatedToTtScanTemp24;
    private String ttScanToBsScanTemp24;
    private String bsScanToShipTemp24;
    private String shipToInventoryTemp24;

//    for timelapse retrieve
    private String requestToVerifiedDate; //received date for SF
    private String verifiedDatetoShipDate; //SF shipping date
    private String shipDateToBsScan;
    private String bsScanToTtScan;

    private String requestToVerifiedDateTemp; //received date for SF
    private String verifiedDatetoShipDateTemp; //SF shipping date
    private String shipDateToBsScanTemp;
    private String bsScanToTtScanTemp;

    private String requestToVerifiedDate24; //received date for SF
    private String verifiedDatetoShipDate24; //SF shipping date
    private String shipDateToBsScan24;
    private String bsScanToTtScan24;

    private String requestToVerifiedDateTemp24; //received date for SF
    private String verifiedDatetoShipDateTemp24; //SF shipping date
    private String shipDateToBsScanTemp24;
    private String bsScanToTtScanTemp24;

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

    public String getRequestToApproveTemp() {
        return requestToApproveTemp;
    }

    public void setRequestToApproveTemp(String requestToApproveTemp) {
        this.requestToApproveTemp = requestToApproveTemp;
    }

    public String getApproveToMPCreatedTemp() {
        return approveToMPCreatedTemp;
    }

    public void setApproveToMPCreatedTemp(String approveToMPCreatedTemp) {
        this.approveToMPCreatedTemp = approveToMPCreatedTemp;
    }

    public String getMpCreatedToTtScanTemp() {
        return mpCreatedToTtScanTemp;
    }

    public void setMpCreatedToTtScanTemp(String mpCreatedToTtScanTemp) {
        this.mpCreatedToTtScanTemp = mpCreatedToTtScanTemp;
    }

    public String getTtScanToBsScanTemp() {
        return ttScanToBsScanTemp;
    }

    public void setTtScanToBsScanTemp(String ttScanToBsScanTemp) {
        this.ttScanToBsScanTemp = ttScanToBsScanTemp;
    }

    public String getBsScanToShipTemp() {
        return bsScanToShipTemp;
    }

    public void setBsScanToShipTemp(String bsScanToShipTemp) {
        this.bsScanToShipTemp = bsScanToShipTemp;
    }

    public String getShipToInventoryTemp() {
        return shipToInventoryTemp;
    }

    public void setShipToInventoryTemp(String shipToInventoryTemp) {
        this.shipToInventoryTemp = shipToInventoryTemp;
    }

    public String getRequestToVerifiedDateTemp() {
        return requestToVerifiedDateTemp;
    }

    public void setRequestToVerifiedDateTemp(String requestToVerifiedDateTemp) {
        this.requestToVerifiedDateTemp = requestToVerifiedDateTemp;
    }

    public String getVerifiedDatetoShipDateTemp() {
        return verifiedDatetoShipDateTemp;
    }

    public void setVerifiedDatetoShipDateTemp(String verifiedDatetoShipDateTemp) {
        this.verifiedDatetoShipDateTemp = verifiedDatetoShipDateTemp;
    }

    public String getShipDateToBsScanTemp() {
        return shipDateToBsScanTemp;
    }

    public void setShipDateToBsScanTemp(String shipDateToBsScanTemp) {
        this.shipDateToBsScanTemp = shipDateToBsScanTemp;
    }

    public String getBsScanToTtScanTemp() {
        return bsScanToTtScanTemp;
    }

    public void setBsScanToTtScanTemp(String bsScanToTtScanTemp) {
        this.bsScanToTtScanTemp = bsScanToTtScanTemp;
    }

    public String getRequestToApprove24() {
        return requestToApprove24;
    }

    public void setRequestToApprove24(String requestToApprove24) {
        this.requestToApprove24 = requestToApprove24;
    }

    public String getApproveToMPCreated24() {
        return approveToMPCreated24;
    }

    public void setApproveToMPCreated24(String approveToMPCreated24) {
        this.approveToMPCreated24 = approveToMPCreated24;
    }

    public String getMpCreatedToTtScan24() {
        return mpCreatedToTtScan24;
    }

    public void setMpCreatedToTtScan24(String mpCreatedToTtScan24) {
        this.mpCreatedToTtScan24 = mpCreatedToTtScan24;
    }

    public String getTtScanToBsScan24() {
        return ttScanToBsScan24;
    }

    public void setTtScanToBsScan24(String ttScanToBsScan24) {
        this.ttScanToBsScan24 = ttScanToBsScan24;
    }

    public String getBsScanToShip24() {
        return bsScanToShip24;
    }

    public void setBsScanToShip24(String bsScanToShip24) {
        this.bsScanToShip24 = bsScanToShip24;
    }

    public String getShipToInventory24() {
        return shipToInventory24;
    }

    public void setShipToInventory24(String shipToInventory24) {
        this.shipToInventory24 = shipToInventory24;
    }

    public String getRequestToVerifiedDate24() {
        return requestToVerifiedDate24;
    }

    public void setRequestToVerifiedDate24(String requestToVerifiedDate24) {
        this.requestToVerifiedDate24 = requestToVerifiedDate24;
    }

    public String getVerifiedDatetoShipDate24() {
        return verifiedDatetoShipDate24;
    }

    public void setVerifiedDatetoShipDate24(String verifiedDatetoShipDate24) {
        this.verifiedDatetoShipDate24 = verifiedDatetoShipDate24;
    }

    public String getShipDateToBsScan24() {
        return shipDateToBsScan24;
    }

    public void setShipDateToBsScan24(String shipDateToBsScan24) {
        this.shipDateToBsScan24 = shipDateToBsScan24;
    }

    public String getBsScanToTtScan24() {
        return bsScanToTtScan24;
    }

    public void setBsScanToTtScan24(String bsScanToTtScan24) {
        this.bsScanToTtScan24 = bsScanToTtScan24;
    }

    public String getRequestToApproveTemp24() {
        return requestToApproveTemp24;
    }

    public void setRequestToApproveTemp24(String requestToApproveTemp24) {
        this.requestToApproveTemp24 = requestToApproveTemp24;
    }

    public String getApproveToMPCreatedTemp24() {
        return approveToMPCreatedTemp24;
    }

    public void setApproveToMPCreatedTemp24(String approveToMPCreatedTemp24) {
        this.approveToMPCreatedTemp24 = approveToMPCreatedTemp24;
    }

    public String getMpCreatedToTtScanTemp24() {
        return mpCreatedToTtScanTemp24;
    }

    public void setMpCreatedToTtScanTemp24(String mpCreatedToTtScanTemp24) {
        this.mpCreatedToTtScanTemp24 = mpCreatedToTtScanTemp24;
    }

    public String getTtScanToBsScanTemp24() {
        return ttScanToBsScanTemp24;
    }

    public void setTtScanToBsScanTemp24(String ttScanToBsScanTemp24) {
        this.ttScanToBsScanTemp24 = ttScanToBsScanTemp24;
    }

    public String getBsScanToShipTemp24() {
        return bsScanToShipTemp24;
    }

    public void setBsScanToShipTemp24(String bsScanToShipTemp24) {
        this.bsScanToShipTemp24 = bsScanToShipTemp24;
    }

    public String getShipToInventoryTemp24() {
        return shipToInventoryTemp24;
    }

    public void setShipToInventoryTemp24(String shipToInventoryTemp24) {
        this.shipToInventoryTemp24 = shipToInventoryTemp24;
    }

    public String getRequestToVerifiedDateTemp24() {
        return requestToVerifiedDateTemp24;
    }

    public void setRequestToVerifiedDateTemp24(String requestToVerifiedDateTemp24) {
        this.requestToVerifiedDateTemp24 = requestToVerifiedDateTemp24;
    }

    public String getVerifiedDatetoShipDateTemp24() {
        return verifiedDatetoShipDateTemp24;
    }

    public void setVerifiedDatetoShipDateTemp24(String verifiedDatetoShipDateTemp24) {
        this.verifiedDatetoShipDateTemp24 = verifiedDatetoShipDateTemp24;
    }

    public String getShipDateToBsScanTemp24() {
        return shipDateToBsScanTemp24;
    }

    public void setShipDateToBsScanTemp24(String shipDateToBsScanTemp24) {
        this.shipDateToBsScanTemp24 = shipDateToBsScanTemp24;
    }

    public String getBsScanToTtScanTemp24() {
        return bsScanToTtScanTemp24;
    }

    public void setBsScanToTtScanTemp24(String bsScanToTtScanTemp24) {
        this.bsScanToTtScanTemp24 = bsScanToTtScanTemp24;
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

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getLoadCard() {
        return loadCard;
    }

    public void setLoadCard(String loadCard) {
        this.loadCard = loadCard;
    }

    public String getProgramCard() {
        return programCard;
    }

    public void setProgramCard(String programCard) {
        this.programCard = programCard;
    }

}
