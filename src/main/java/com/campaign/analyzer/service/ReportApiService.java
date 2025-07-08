package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.ReportData;
import com.campaign.analyzer.entity.ReportResponse;
import com.campaign.analyzer.enums.ReportType;

public interface ReportApiService<T extends ReportData> {
    ReportResponse<T> getReport(String name, String startDate, String endDate, String interval, String bearerToken);

    String extractName(String query);

    ReportType getReportType();
}