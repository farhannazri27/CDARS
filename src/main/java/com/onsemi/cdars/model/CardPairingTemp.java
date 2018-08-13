package com.onsemi.cdars.model;

public class CardPairingTemp {

    private String id;
    private String pairId;
    private String type;
    private String loadCard;
    private String programCard;
    private String createdBy;
    private String createdDate;

    public CardPairingTemp() {
    }

    public CardPairingTemp(String type,
            String programCard,
            String loadCard) {

        super();
        this.type = type;
        this.programCard = programCard;
        this.loadCard = loadCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
