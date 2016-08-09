package com.onsemi.cdars.model;

public class ParameterDetails {

    private String id;
    private String masterCode;
    private String detailCode;
    private String name;
    private String remarks;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private String selected;

    public ParameterDetails() {

    }

    public ParameterDetails(String name) {
        this.name = name;

    }

    public ParameterDetails(String id, String masterCode, String detailCode, String name, String selected) {
        this.id = id;
        this.masterCode = masterCode;
        this.detailCode = detailCode;
        this.name = name;
        this.selected = selected;
    }

    public ParameterDetails(String id, String masterCode, String detailCode, String name, String remarks, String createdBy, String createdDate, String modifiedBy, String modifiedDate) {
        this.id = id;
        this.masterCode = masterCode;
        this.detailCode = detailCode;
        this.name = name;
        this.remarks = remarks;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterCode() {
        return masterCode;
    }

    public void setMasterCode(String masterCode) {
        this.masterCode = masterCode;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

}
