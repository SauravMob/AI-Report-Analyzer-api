package com.campaign.analyzer.entity;

public class AnalyzeResponse {
    private String analysis;
    private boolean success;
    private String error;

    public AnalyzeResponse() {
    }

    public AnalyzeResponse(String analysis, boolean success) {
        this.analysis = analysis;
        this.success = success;
    }

    public AnalyzeResponse(String error) {
        this.error = error;
        this.success = false;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}