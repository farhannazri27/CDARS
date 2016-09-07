package com.onsemi.cdars.model;

public class WhRetrievalTemp {

    private String requestId;
    private String mpNo;
    private String verifiedBy;
    private String verifiedDate;
    private String shippingBy;
    private String shippingDate;
    private String status;

    public WhRetrievalTemp(String requestId,
            String mpNo,
            String verifiedDate,
            String verifiedBy,
            String shippingDate,
            String shippingBy,
            String status) {

        super();
        this.requestId = requestId;
        this.mpNo = mpNo;
        this.verifiedDate = verifiedDate;
        this.verifiedBy = verifiedBy;
        this.shippingDate = shippingDate;
        this.shippingBy = shippingBy;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
