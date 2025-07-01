package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CampaignData implements ReportData {
    @JsonProperty("bids")
    private Long bids;

    @JsonProperty("campaignId")
    private Long campaignId;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("clicks")
    private Long clicks;

    @JsonProperty("conversions")
    private Long conversions;

    @JsonProperty("ctr")
    private Double ctr;

    @JsonProperty("dailyBudget")
    private Double dailyBudget;

    @JsonProperty("date")
    private String date;

    @JsonProperty("ecpa")
    private Double ecpa;

    @JsonProperty("ecpi")
    private Double ecpi;

    @JsonProperty("ecpm")
    private Double ecpm;

    @JsonProperty("impressions")
    private Long impressions;

    @JsonProperty("purchaseConversions")
    private Long purchaseConversions;

    @JsonProperty("registrationConversions")
    private Long registrationConversions;

    @JsonProperty("repeatEventConversions")
    private Long repeatEventConversions;

    @JsonProperty("skanAttributed")
    private Long skanAttributed;

    @JsonProperty("skanUnAttributed")
    private Long skanUnAttributed;

    @JsonProperty("spends")
    private Double spends;

    @JsonProperty("vcompletionCount")
    private Long vcompletionCount;

    @JsonProperty("vfirstQuarter")
    private Long vfirstQuarter;

    @JsonProperty("vmidPoint")
    private Long vmidPoint;

    @JsonProperty("vmidRoll")
    private Long vmidRoll;

    @JsonProperty("vpostRoll")
    private Long vpostRoll;

    @JsonProperty("vpreRoll")
    private Long vpreRoll;

    @JsonProperty("vstart")
    private Long vstart;

    @JsonProperty("vstartDelay")
    private Long vstartDelay;

    @JsonProperty("vthirdQuarter")
    private Long vthirdQuarter;

    @JsonProperty("winRate")
    private Double winRate;

    @Override
    public String getName() {
        return campaignName;
    }

    public Long getClicks() {
        return clicks;
    }

    public Double getCtr() {
        return ctr;
    }

    public String getDate() {
        return date;
    }

    public Long getImpressions() {
        return impressions;
    }

    public Double getSpends() {
        return spends;
    }
}
