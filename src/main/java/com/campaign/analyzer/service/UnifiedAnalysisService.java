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
public class UnifiedAnalysisService {

    @Autowired
    private EntityExtractionService entityExtractionService;

    @Autowired
    private EntityClassificationService entityClassificationService;

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

    public String analyzeQuery(String query, String bearerToken) {
        try {
            // Step 1: Extract entity name using AI
            String entityName = entityExtractionService.extractEntityName(query);
            if (entityName == null || entityName.trim().isEmpty()) {
                return "I couldn't identify a campaign or creative name in your query. Please provide a clear entity name for analysis.";
            }

            System.out.println("Extracted entity: " + entityName);

            // Step 2: Classify entity type using AI
            ReportType primaryType = entityClassificationService.classifyEntity(entityName, query);
            ReportType fallbackType = (primaryType == ReportType.CAMPAIGN) ? ReportType.CREATIVE : ReportType.CAMPAIGN;

            System.out.println("Classified as: " + primaryType + ", fallback: " + fallbackType);

            // Step 3: Extract date range
            String[] dateRange = dateUtils.extractDateRange(query);
            String startDate = dateRange[0];
            String endDate = dateRange[1];
            String interval = dateRange[2];

            // Step 4: Try primary classification first
            ReportResponse<? extends ReportData> reportResponse = tryGetReport(entityName, startDate, endDate, interval, bearerToken, primaryType);
            ReportType actualType = primaryType;

            // Step 5: Fallback to secondary type if no data found
            if (reportResponse == null || reportResponse.getContent() == null || reportResponse.getContent().isEmpty()) {
                System.out.println("No data found for " + primaryType + ", trying " + fallbackType);
                reportResponse = tryGetReport(entityName, startDate, endDate, interval, bearerToken, fallbackType);
                actualType = fallbackType;
            }

            // Step 6: Generate response
            if (reportResponse == null || reportResponse.getContent() == null || reportResponse.getContent().isEmpty()) {
                return emptyDataResponse.generateNoDataResponse(entityName, startDate, endDate);
            }

            String analysisPrompt = buildAnalysisPrompt(entityName, reportResponse.getContent(), startDate, endDate, query, actualType);
            System.out.println(analysisPrompt);
            String rawAnalysis = ollamaService.generateAnalysis(analysisPrompt);
            return cleanAnalysis(rawAnalysis);

        } catch (Exception e) {
            System.err.println("Error in analyzeQuery: " + e.getMessage());
            e.printStackTrace();
            return "Error analyzing data: " + e.getMessage();
        }
    }

    private ReportResponse<? extends ReportData> tryGetReport(String entityName, String startDate, String endDate,
                                                              String interval, String bearerToken, ReportType reportType) {
        try {
            ReportApiService<? extends ReportData> apiService = getApiService(reportType);
            return apiService.getReport(entityName, startDate, endDate, interval, bearerToken);
        } catch (Exception e) {
            System.err.println("Failed to get " + reportType + " report: " + e.getMessage());
            return null;
        }
    }

    private String buildAnalysisPrompt(String entityName, List<? extends ReportData> reportData,
                                       String startDate, String endDate, String originalQuery, ReportType actualType) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a digital marketing analyst. Analyze the following ").append(actualType.name().toLowerCase())
                .append(" performance data and provide insights.\n\n");
        prompt.append("Original Query: ").append(originalQuery).append("\n");
        prompt.append(actualType.name().substring(0, 1).toUpperCase())
                .append(actualType.name().substring(1).toLowerCase()).append(": ").append(entityName).append("\n");
        prompt.append("Date Range: ").append(startDate).append(" to ").append(endDate).append("\n\n");

        // Calculate totals
        long totalImpressions = reportData.stream().mapToLong(d -> d.getImpressions() != null ? d.getImpressions() : 0).sum();
        long totalClicks = reportData.stream().mapToLong(d -> d.getClicks() != null ? d.getClicks() : 0).sum();
        double totalSpends = reportData.stream().mapToDouble(d -> d.getSpends() != null ? d.getSpends() : 0).sum();
        double avgCtr = reportData.stream().mapToDouble(d -> d.getCtr() != null ? d.getCtr() : 0).average().orElse(0);

        prompt.append("Performance Metrics:\n");
        prompt.append("- Total Impressions: ").append(String.format("%,d", totalImpressions)).append("\n");
        prompt.append("- Total Clicks: ").append(String.format("%,d", totalClicks)).append("\n");
        prompt.append("- Total Spend: $").append(String.format("%.2f", totalSpends)).append("\n");
        prompt.append("- Average CTR: ").append(String.format("%.4f", avgCtr)).append("%\n");

        if (totalClicks > 0 && totalSpends > 0) {
            double avgCpc = totalSpends / totalClicks;
            prompt.append("- Average CPC: $").append(String.format("%.4f", avgCpc)).append("\n");
        }

        if (totalImpressions > 0 && totalSpends > 0) {
            double avgCpm = (totalSpends / totalImpressions) * 1000;
            prompt.append("- Average CPM: $").append(String.format("%.2f", avgCpm)).append("\n");
        }

        prompt.append("\nDaily Breakdown:\n");
        for (ReportData data : reportData) {
            prompt.append("Date: ").append(data.getDate())
                    .append(" | Impressions: ").append(String.format("%,d", data.getImpressions()))
                    .append(" | Clicks: ").append(String.format("%,d", data.getClicks()))
                    .append(" | Spend: $").append(String.format("%.2f", data.getSpends()))
                    .append(" | CTR: ").append(String.format("%.4f", data.getCtr())).append("%\n");
        }

        prompt.append("\nPlease provide a comprehensive analysis including:\n");
        prompt.append("1. Overall performance assessment\n");
        prompt.append("2. Key insights and trends over the date range\n");
        prompt.append("3. Areas of strength and potential concerns\n");
        prompt.append("4. Actionable recommendations for optimization\n");
        prompt.append("5. Notable patterns or anomalies in the daily data\n\n");
        prompt.append("Keep the analysis concise but informative, focusing on actionable insights and business value.");

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