package com.onsemi.cdars.tools;

public class QueryResult {
    private Integer result;
    private String errorMessage;
    private String generatedKey;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(String generatedKey) {
        this.generatedKey = generatedKey;
    }
}
