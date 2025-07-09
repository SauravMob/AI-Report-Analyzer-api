package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.ReportData;
import com.campaign.analyzer.entity.ReportResponse;
import com.campaign.analyzer.enums.ReportType;
import com.campaign.analyzer.utils.DateAndIntervalUtil;
import com.campaign.analyzer.utils.EmptyDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisService {

    @Autowired
    private CampaignApiService campaignApiService;

    @Autowired
    private CreativeApiService creativeApiService;

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private EmptyDataResponse emptyDataResponse;

    @Autowired
    private DateAndIntervalUtil dateUtils;

    public String analyzeQuery(String query, String bearerToken, ReportType reportType) {
        try {
            ReportApiService<? extends ReportData> apiService = getApiService(reportType);
            String name = apiService.extractName(query);
            if (name == null) {
                return String.format("I couldn't identify a %s name in your query.", reportType.name().toLowerCase());
            }

            String[] dateRange = dateUtils.extractDateRange(query);
            String startDate = dateRange[0];
            String endDate = dateRange[1];
            String interval = dateRange[2];

            // Fetch report data
            ReportResponse<? extends ReportData> reportResponse = apiService.getReport(name, startDate, endDate, interval, bearerToken);

            System.out.println("ReportResponse: " + reportResponse);

            if (reportResponse == null || reportResponse.getContent() == null || reportResponse.getContent().isEmpty()) {
                return emptyDataResponse.generateNoDataResponse(name, startDate, endDate);
            }

            String analysisPrompt = buildGenericAnalysisPrompt(name, reportResponse.getContent(), startDate, endDate, query, reportType);
            String rawAnalysis = ollamaService.generateAnalysis(analysisPrompt);
            return cleanAnalysis(rawAnalysis);
        } catch (Exception e) {
            return "Error analyzing campaign data: " + e.getMessage();
        }
    }

    private String buildGenericAnalysisPrompt(String name, List<? extends ReportData> reportData,
                                              String startDate, String endDate, String originalQuery, ReportType reportType) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a digital marketing analyst. Analyze the following ").append(reportType.name().toLowerCase())
                .append(" performance data and provide insights.\n\n");
        prompt.append("Original Query: ").append(originalQuery).append("\n");
        prompt.append(reportType.name().substring(0, 1).toUpperCase())
                .append(reportType.name().substring(1).toLowerCase()).append(": ").append(name).append("\n");
        prompt.append("Date Range: ").append(startDate).append(" to ").append(endDate).append("\n\n");

        // Calculate totals
        long totalImpressions = reportData.stream().mapToLong(d -> d.getImpressions() != null ? d.getImpressions() : 0).sum();
        long totalClicks = reportData.stream().mapToLong(d -> d.getClicks() != null ? d.getClicks() : 0).sum();
        double totalSpends = reportData.stream().mapToDouble(d -> d.getSpends() != null ? d.getSpends() : 0).sum();
        double avgCtr = reportData.stream().mapToDouble(d -> d.getCtr() != null ? d.getCtr() : 0).average().orElse(0);

        prompt.append("Performance Metrics:\n");
        prompt.append("- Total Impressions: ").append(totalImpressions).append("\n");
        prompt.append("- Total Clicks: ").append(totalClicks).append("\n");
        prompt.append("- Total Spend: $").append(String.format("%.2f", totalSpends)).append("\n");
        prompt.append("- Average CTR: ").append(String.format("%.4f", avgCtr)).append("%\n");

        if (totalClicks > 0) {
            // Add conversion rate if available (you may need to extract conversions from specific data types)
        }

        prompt.append("\nDaily Breakdown:\n");
        for (ReportData data : reportData) {
            prompt.append("Date: ").append(data.getDate())
                    .append(" | Impressions: ").append(data.getImpressions())
                    .append(" | Clicks: ").append(data.getClicks())
                    .append(" | Spend: $").append(data.getSpends())
                    .append(" | CTR: ").append(data.getCtr()).append("%\n");
        }

        prompt.append("\nPlease provide a comprehensive analysis including:\n");
        prompt.append("1. Overall performance assessment\n");
        prompt.append("2. Key insights and trends\n");
        prompt.append("3. Areas of strength and concern\n");
        prompt.append("4. Actionable recommendations for optimization\n");
        prompt.append("5. Any notable patterns in the daily data\n\n");
        prompt.append("Keep the analysis concise but informative, focusing on actionable insights.");

        return prompt.toString();
    }

    private String cleanAnalysis(String rawAnalysis) {
        return rawAnalysis
                .replaceAll("<think>.*?</think>", "") // Remove thinking blocks
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1") // Remove bold markdown
                .replaceAll("\\n-", "\n•") // Convert dashes to bullets
                .replaceAll("\\n{3,}", "\n\n") // Clean extra newlines
                .trim();
    }

    private ReportApiService<? extends ReportData> getApiService(ReportType reportType) {
        if (reportType == ReportType.CAMPAIGN) {
            return campaignApiService;
        } else if (reportType == ReportType.CREATIVE) {
            return creativeApiService;
        }
        throw new IllegalArgumentException("Unsupported report type: " + reportType);
    }
}
