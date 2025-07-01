package com.campaign.analyzer.entity;

public class AnalyzeRequest {
    private String query;
    private String bearerToken;

    public String getQuery() {
        return query;
    }

    public String getBearerToken() {
        return bearerToken;
    }
}