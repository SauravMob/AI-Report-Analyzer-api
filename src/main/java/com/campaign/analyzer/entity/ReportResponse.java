package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.List;

@ToString
public class ReportResponse<D> {
    @JsonProperty("content")
    private List<D> content;

    @JsonProperty("last")
    private Boolean last;

    @JsonProperty("pageNo")
    private Integer pageNo;

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("totalElements")
    private Long totalElements;

    @JsonProperty("totalPages")
    private Integer totalPages;

    // Getters and Setters
    public List<D> getContent() {
        return content;
    }
}
