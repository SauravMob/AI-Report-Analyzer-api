package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
}
