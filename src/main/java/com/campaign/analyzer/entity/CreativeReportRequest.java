package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreativeReportRequest {
    @JsonProperty("campaignId")
    private Long campaignId = 0L;

    @JsonProperty("creativeId")
    private Long creativeId = 0L;

    @JsonProperty("creativeName")
    private String creativeName;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("interval")
    private String interval;

    @JsonProperty("publisherId")
    private Long publisherId = 0L;

    @JsonProperty("reportType")
    private String reportType;

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
