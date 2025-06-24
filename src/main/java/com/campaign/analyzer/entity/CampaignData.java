package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CampaignData {
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

    // Getters and Setters (generated for all fields)
    public Long getBids() {
        return bids;
    }

    public void setBids(Long bids) {
        this.bids = bids;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getConversions() {
        return conversions;
    }

    public void setConversions(Long conversions) {
        this.conversions = conversions;
    }

    public Double getCtr() {
        return ctr;
    }

    public void setCtr(Double ctr) {
        this.ctr = ctr;
    }

    public Double getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(Double dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getEcpa() {
        return ecpa;
    }

    public void setEcpa(Double ecpa) {
        this.ecpa = ecpa;
    }

    public Double getEcpi() {
        return ecpi;
    }

    public void setEcpi(Double ecpi) {
        this.ecpi = ecpi;
    }

    public Double getEcpm() {
        return ecpm;
    }

    public void setEcpm(Double ecpm) {
        this.ecpm = ecpm;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public Long getPurchaseConversions() {
        return purchaseConversions;
    }

    public void setPurchaseConversions(Long purchaseConversions) {
        this.purchaseConversions = purchaseConversions;
    }

    public Long getRegistrationConversions() {
        return registrationConversions;
    }

    public void setRegistrationConversions(Long registrationConversions) {
        this.registrationConversions = registrationConversions;
    }

    public Long getRepeatEventConversions() {
        return repeatEventConversions;
    }

    public void setRepeatEventConversions(Long repeatEventConversions) {
        this.repeatEventConversions = repeatEventConversions;
    }

    public Long getSkanAttributed() {
        return skanAttributed;
    }

    public void setSkanAttributed(Long skanAttributed) {
        this.skanAttributed = skanAttributed;
    }

    public Long getSkanUnAttributed() {
        return skanUnAttributed;
    }

    public void setSkanUnAttributed(Long skanUnAttributed) {
        this.skanUnAttributed = skanUnAttributed;
    }

    public Double getSpends() {
        return spends;
    }

    public void setSpends(Double spends) {
        this.spends = spends;
    }

    public Long getVcompletionCount() {
        return vcompletionCount;
    }

    public void setVcompletionCount(Long vcompletionCount) {
        this.vcompletionCount = vcompletionCount;
    }

    public Long getVfirstQuarter() {
        return vfirstQuarter;
    }

    public void setVfirstQuarter(Long vfirstQuarter) {
        this.vfirstQuarter = vfirstQuarter;
    }

    public Long getVmidPoint() {
        return vmidPoint;
    }

    public void setVmidPoint(Long vmidPoint) {
        this.vmidPoint = vmidPoint;
    }

    public Long getVmidRoll() {
        return vmidRoll;
    }

    public void setVmidRoll(Long vmidRoll) {
        this.vmidRoll = vmidRoll;
    }

    public Long getVpostRoll() {
        return vpostRoll;
    }

    public void setVpostRoll(Long vpostRoll) {
        this.vpostRoll = vpostRoll;
    }

    public Long getVpreRoll() {
        return vpreRoll;
    }

    public void setVpreRoll(Long vpreRoll) {
        this.vpreRoll = vpreRoll;
    }

    public Long getVstart() {
        return vstart;
    }

    public void setVstart(Long vstart) {
        this.vstart = vstart;
    }

    public Long getVstartDelay() {
        return vstartDelay;
    }

    public void setVstartDelay(Long vstartDelay) {
        this.vstartDelay = vstartDelay;
    }

    public Long getVthirdQuarter() {
        return vthirdQuarter;
    }

    public void setVthirdQuarter(Long vthirdQuarter) {
        this.vthirdQuarter = vthirdQuarter;
    }

    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }
}
