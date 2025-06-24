package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CampaignReportRequest {
    @JsonProperty("accountManager")
    private Integer accountManager = 0;

    @JsonProperty("advertiserId")
    private Integer advertiserId = 0;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("country")
    private String country;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("exchange")
    private String exchange;

    @JsonProperty("os")
    private String os;

    @JsonProperty("startDate")
    private String startDate;

    public CampaignReportRequest() {
    }

    public CampaignReportRequest(String campaignName, String startDate, String endDate) {
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(Integer accountManager) {
        this.accountManager = accountManager;
    }

    public Integer getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Integer advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
