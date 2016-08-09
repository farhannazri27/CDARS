package com.onsemi.cdars.model;

public class HardwareRequestTemp {

    private String forecastReadoutStart;
    private String eventCode;
    private String rms;
    private String process;

    public HardwareRequestTemp() {
    }

    public HardwareRequestTemp(String forecastReadoutStart,
            String eventCode,
            String rms,
            String process) {

        super();
        this.forecastReadoutStart = forecastReadoutStart;
        this.eventCode = eventCode;
        this.rms = rms;
        this.process = process;
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

}
