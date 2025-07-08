package com.campaign.analyzer.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyzeResponse {
    private String analysis;
    private boolean success;
    private String error;

    public AnalyzeResponse(String analysis, boolean success) {
        this.analysis = analysis;
        this.success = success;
    }

    public AnalyzeResponse(String error) {
        this.error = error;
    }
}