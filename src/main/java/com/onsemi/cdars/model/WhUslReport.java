package com.onsemi.cdars.model;

public class WhUslReport {

    private String requestId;
    private String eqptType;
    private String eqptId;
    private String mpNo;
    private String loadCard;
    private String programCard;
    private String activity;
    private String count;
    private String requestDate;

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

//    for timelapse ship
    private String reqToApp;
    private String mpCreateToTtScan;
    private String ttScanToBcScan;
    private String bcScanToShip;
    private String shipToInv;
    private String totalHourTakeShip;
    private String statusUsl;
    private String tiId;

    public String getTiId() {
        return tiId;
    }

    public void setTiId(String tiId) {
        this.tiId = tiId;
    }

    public String getStatusUsl() {
        return statusUsl;
    }

    public void setStatusUsl(String statusUsl) {
        this.statusUsl = statusUsl;
    }

    //for timelapse retrieve
    private String reqToVer;
    private String verToShip;
    private String shipToBcScan;
    private String bcScanToTtScan;
    private String totalHourTakeRetrieve;

    private String ca;
    private String rootCause;
    private String createdBy;
    private String createdDate;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
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

    public String getTotalHourTakeShip() {
        return totalHourTakeShip;
    }

    public void setTotalHourTakeShip(String totalHourTakeShip) {
        this.totalHourTakeShip = totalHourTakeShip;
    }

    public String getReqToVer() {
        return reqToVer;
    }

    public void setReqToVer(String reqToVer) {
        this.reqToVer = reqToVer;
    }

    public String getVerToShip() {
        return verToShip;
    }

    public void setVerToShip(String verToShip) {
        this.verToShip = verToShip;
    }

    public String getShipToBcScan() {
        return shipToBcScan;
    }

    public void setShipToBcScan(String shipToBcScan) {
        this.shipToBcScan = shipToBcScan;
    }

    public String getBcScanToTtScan() {
        return bcScanToTtScan;
    }

    public void setBcScanToTtScan(String bcScanToTtScan) {
        this.bcScanToTtScan = bcScanToTtScan;
    }

    public String getTotalHourTakeRetrieve() {
        return totalHourTakeRetrieve;
    }

    public void setTotalHourTakeRetrieve(String totalHourTakeRetrieve) {
        this.totalHourTakeRetrieve = totalHourTakeRetrieve;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getEqptType() {
        return eqptType;
    }

    public void setEqptType(String eqptType) {
        this.eqptType = eqptType;
    }

    public String getEqptId() {
        return eqptId;
    }

    public void setEqptId(String eqptId) {
        this.eqptId = eqptId;
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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getReqToApp() {
        return reqToApp;
    }

    public void setReqToApp(String reqToApp) {
        this.reqToApp = reqToApp;
    }

    public String getMpCreateToTtScan() {
        return mpCreateToTtScan;
    }

    public void setMpCreateToTtScan(String mpCreateToTtScan) {
        this.mpCreateToTtScan = mpCreateToTtScan;
    }

    public String getTtScanToBcScan() {
        return ttScanToBcScan;
    }

    public void setTtScanToBcScan(String ttScanToBcScan) {
        this.ttScanToBcScan = ttScanToBcScan;
    }

    public String getBcScanToShip() {
        return bcScanToShip;
    }

    public void setBcScanToShip(String bcScanToShip) {
        this.bcScanToShip = bcScanToShip;
    }

    public String getShipToInv() {
        return shipToInv;
    }

    public void setShipToInv(String shipToInv) {
        this.shipToInv = shipToInv;
    }

}
