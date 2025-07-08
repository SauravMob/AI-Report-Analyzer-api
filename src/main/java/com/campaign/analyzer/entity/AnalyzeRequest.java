package com.campaign.analyzer.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyzeRequest {
    private String query;
    private String bearerToken;
}