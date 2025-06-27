package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteAppData {

    @JsonProperty("adFormat")
    private Long adFormat;

    @JsonProperty("adslotId")
    private String adslotId;

    @JsonProperty("bids")
    private Long bids;

    @JsonProperty("bundleId")
    private String bundleId;

    @JsonProperty("campaignId")
    private Long campaignId;

    @JsonProperty("campaignName")
    private String campaignName;

    @JsonProperty("clicks")
    private Long clicks;

    @JsonProperty("conversions")
    private Long conversions;

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

    @JsonProperty("isSkippable")
    private Integer isSkippable;

    @JsonProperty("publisherId")
    private Long publisherId;

    @JsonProperty("purchaseConversions")
    private Long purchaseConversions;

    @JsonProperty("registrationConversions")
    private Long registrationConversions;

    @JsonProperty("repeatEventConversions")
    private Long repeatEventConversions;

    @JsonProperty("siteAppId")
    private Long siteAppId;

    @JsonProperty("siteAppName")
    private String siteAppName;

    @JsonProperty("spends")
    private Double spends;

    @JsonProperty("sspName")
    private String sspName;

    @JsonProperty("videoCompletion")
    private Long videoCompletion;

    @JsonProperty("winRate")
    private Double winRate;

    public String getAdslotId() {
        return adslotId;
    }

    public void setAdslotId(String adslotId) {
        this.adslotId = adslotId;
    }

    public Long getAdFormat() {
        return adFormat;
    }

    public void setAdFormat(Long adFormat) {
        this.adFormat = adFormat;
    }

    public Long getBids() {
        return bids;
    }

    public void setBids(Long bids) {
        this.bids = bids;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
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

    public Integer getIsSkippable() {
        return isSkippable;
    }

    public void setIsSkippable(Integer isSkippable) {
        this.isSkippable = isSkippable;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
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

    public Long getSiteAppId() {
        return siteAppId;
    }

    public void setSiteAppId(Long siteAppId) {
        this.siteAppId = siteAppId;
    }

    public String getSiteAppName() {
        return siteAppName;
    }

    public void setSiteAppName(String siteAppName) {
        this.siteAppName = siteAppName;
    }

    public Double getSpends() {
        return spends;
    }

    public void setSpends(Double spends) {
        this.spends = spends;
    }

    public String getSspName() {
        return sspName;
    }

    public void setSspName(String sspName) {
        this.sspName = sspName;
    }

    public Long getVideoCompletion() {
        return videoCompletion;
    }

    public void setVideoCompletion(Long videoCompletion) {
        this.videoCompletion = videoCompletion;
    }

    public Double getWinRate() {
        return winRate;
    }

    public void setWinRate(Double winRate) {
        this.winRate = winRate;
    }
}
