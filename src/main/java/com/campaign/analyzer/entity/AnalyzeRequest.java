package com.campaign.analyzer.entity;

public class AnalyzeRequest {
    private String query;
    private String bearerToken;

    public AnalyzeRequest() {
    }

    public AnalyzeRequest(String query, String bearerToken) {
        this.query = query;
        this.bearerToken = bearerToken;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
