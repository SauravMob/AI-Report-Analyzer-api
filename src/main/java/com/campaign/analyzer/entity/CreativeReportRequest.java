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
}
