package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CampaignReportResponse {
    @JsonProperty("content")
    private List<CampaignData> content;

    @JsonProperty("last")
    private Boolean last;

    @JsonProperty("pageNo")
    private Integer pageNo;

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("totalElements")
    private Long totalElements;

    @JsonProperty("totalPages")
    private Integer totalPages;

    // Getters and Setters
    public List<CampaignData> getContent() {
        return content;
    }
}
