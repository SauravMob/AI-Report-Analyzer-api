package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteAppReportRequest {
    @JsonProperty("adFormat")
    private Long adFormat;

    @JsonProperty("adslotId")
    private String adslotId;

    @JsonProperty("advertiserId")
    private Long advertiserId;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("creativeName")
    private String creativeName;

    @JsonProperty("exchangeId")
    private Long exchangeId;

    @JsonProperty("from")
    private String from;

    @JsonProperty("interval")
    private String interval;

    @JsonProperty("isSkippable")
    private Integer isSkippable;

    @JsonProperty("reportType")
    private String reportType;

    @JsonProperty("to")
    private String to;

    public Long getAdFormat() {
        return adFormat;
    }

    public void setAdFormat(Long adFormat) {
        this.adFormat = adFormat;
    }

    public String getAdslotId() {
        return adslotId;
    }

    public void setAdslotId(String adslotId) {
        this.adslotId = adslotId;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Long advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getIsSkippable() {
        return isSkippable;
    }

    public void setIsSkippable(Integer isSkippable) {
        this.isSkippable = isSkippable;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
