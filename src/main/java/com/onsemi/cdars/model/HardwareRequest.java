package com.onsemi.cdars.model;

public class HardwareRequest {

	private String id;
	private String forecastReadoutStart;
	private String eventCode;
	private String rms;
	private String process;
	private String status;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;
        private String forecastDateView;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForecastReadoutStart() {
		return forecastReadoutStart;
	}

	public void setForecastReadoutStart(String forecastReadoutStart) {
		this.forecastReadoutStart = forecastReadoutStart;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getRms() {
		return rms;
	}

	public void setRms(String rms) {
		this.rms = rms;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

    public String getForecastDateView() {
        return forecastDateView;
    }

    public void setForecastDateView(String forecastDateView) {
        this.forecastDateView = forecastDateView;
    }
        

}