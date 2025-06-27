package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreativeData {

    @JsonProperty("bids")
    private Long bids;

    @JsonProperty("campaignId")
    private Long campaignId;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("clicks")
    private Long clicks;

    @JsonProperty("creativeId")
    private Long creativeId;

    @JsonProperty("creativeName")
    private String creativeName;

    @JsonProperty("ctr")
    private Double ctr;

    @JsonProperty("date")
    private String date;

    @JsonProperty("ecpm")
    private Double ecpm;

    @JsonProperty("impressions")
    private Long impressions;

    @JsonProperty("installs")
    private Long installs;

    @JsonProperty("purchaseConversions")
    private Long purchaseConversions;

    @JsonProperty("registrationConversions")
    private Long registrationConversions;

    @JsonProperty("repeatEventConversions")
    private Long repeatEventConversions;

    @JsonProperty("spends")
    private Double spends;

    @JsonProperty("vfirstQuarter")
    private Long vfirstQuarter;

    @JsonProperty("videoCompletion")
    private Long videoCompletion;

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

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Long getBids() {
        return bids;
    }

    public void setBids(Long bids) {
        this.bids = bids;
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

    public Long getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Long creativeId) {
        this.creativeId = creativeId;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }

    public Double getCtr() {
        return ctr;
    }

    public void setCtr(Double ctr) {
        this.ctr = ctr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Long getInstalls() {
        return installs;
    }

    public void setInstalls(Long installs) {
        this.installs = installs;
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

    public Double getSpends() {
        return spends;
    }

    public void setSpends(Double spends) {
        this.spends = spends;
    }

    public Long getVfirstQuarter() {
        return vfirstQuarter;
    }

    public void setVfirstQuarter(Long vfirstQuarter) {
        this.vfirstQuarter = vfirstQuarter;
    }

    public Long getVideoCompletion() {
        return videoCompletion;
    }

    public void setVideoCompletion(Long videoCompletion) {
        this.videoCompletion = videoCompletion;
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
}