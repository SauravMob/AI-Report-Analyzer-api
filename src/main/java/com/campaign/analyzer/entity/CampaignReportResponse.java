package com.campaign.analyzer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CampaignReportResponse {
    @JsonProperty("content")
    private List<CampaignData> content;

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
    public List<CampaignData> getContent() {
        return content;
    }

    public void setContent(List<CampaignData> content) {
        this.content = content;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
