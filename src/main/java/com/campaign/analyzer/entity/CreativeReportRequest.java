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

    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
