package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
