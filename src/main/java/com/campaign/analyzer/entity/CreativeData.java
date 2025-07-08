package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreativeData implements ReportData {

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

    @Override
    public String getName() {
        return creativeName;
    }
}