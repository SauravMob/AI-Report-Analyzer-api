package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.AnalyzeResponse;
import com.campaign.analyzer.entity.CampaignData;
import com.campaign.analyzer.entity.CampaignReportResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisService {

    @Autowired
    private QueryParserService queryParserService;

    @Autowired
    private CampaignApiService campaignApiService;

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private ObjectMapper objectMapper;

    public String analyzeQuery(String query, String bearerToken) {
        try {
            // Extract campaign name and date range from query
            String campaignName = queryParserService.extractCampaignName(query);
            if (campaignName == null) {
                return "I couldn't identify a campaign name in your query.";
            }

            String[] dateRange = queryParserService.extractDateRange(query);
            String startDate = dateRange[0];
            String endDate = dateRange[1];

            // Fetch campaign data
            CampaignReportResponse reportResponse = campaignApiService.getCampaignReport(campaignName, startDate, endDate, bearerToken);

            if (reportResponse == null || reportResponse.getContent() == null || reportResponse.getContent().isEmpty()) {
                return generateNoDataResponse(campaignName, startDate, endDate);
            }

            // Generate analysis prompt
            String analysisPrompt = buildAnalysisPrompt(campaignName, reportResponse.getContent(), startDate, endDate, query);

            // Get AI analysis
            String rawAnalysis = ollamaService.generateAnalysis(analysisPrompt);

            return rawAnalysis
                    .replaceAll("<think>.*?</think>", "") // Remove thinking blocks
                    .replaceAll("\\*\\*(.*?)\\*\\*", "$1") // Remove bold markdown
                    .replaceAll("\\n-", "\n•") // Convert dashes to bullets
                    .replaceAll("\\n{3,}", "\n\n") // Clean extra newlines
                    .trim();

        } catch (Exception e) {
            return "Error analyzing campaign data: " + e.getMessage();
        }
    }

    private String cleanAnalysisResponse(String rawResponse) {
        System.out.println("RawResponse: " + rawResponse);
        if (rawResponse == null) return "No analysis available";

        // Remove <think> blocks
        String cleaned = rawResponse.replaceAll("<think>.*?</think>", "").trim();

        // Convert markdown headers to plain text
        cleaned = cleaned.replaceAll("\\*\\*(.*?)\\*\\*", "$1"); // Bold to plain
        cleaned = cleaned.replaceAll("^#{1,6}\\s*", ""); // Remove markdown headers

        // Clean up extra newlines and spacing
        cleaned = cleaned.replaceAll("\\n{3,}", "\n\n"); // Max 2 newlines
        cleaned = cleaned.replaceAll("^\\s+|\\s+$", ""); // Trim whitespace

        return cleaned;
    }

    private String buildAnalysisPrompt(String campaignName, List<CampaignData> campaignData,
                                       String startDate, String endDate, String originalQuery) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a digital marketing analyst. Analyze the following campaign performance data and provide insights.\n\n");
        prompt.append("Original Query: ").append(originalQuery).append("\n");
        prompt.append("Campaign: ").append(campaignName).append("\n");
        prompt.append("Date Range: ").append(startDate).append(" to ").append(endDate).append("\n\n");

        // Calculate totals
        long totalImpressions = 0;
        long totalClicks = 0;
        long totalConversions = 0;
        double totalSpends = 0;
        long totalBids = 0;
        double avgCtr = 0;
        double avgWinRate = 0;

        for (CampaignData data : campaignData) {
            totalImpressions += (data.getImpressions() != null ? data.getImpressions() : 0);
            totalClicks += (data.getClicks() != null ? data.getClicks() : 0);
            totalConversions += (data.getConversions() != null ? data.getConversions() : 0);
            totalSpends += (data.getSpends() != null ? data.getSpends() : 0);
            totalBids += (data.getBids() != null ? data.getBids() : 0);
            avgCtr += (data.getCtr() != null ? data.getCtr() : 0);
            avgWinRate += (data.getWinRate() != null ? data.getWinRate() : 0);
        }

        avgCtr = campaignData.size() > 0 ? avgCtr / campaignData.size() : 0;
        avgWinRate = campaignData.size() > 0 ? avgWinRate / campaignData.size() : 0;

        prompt.append("Campaign Performance Metrics:\n");
        prompt.append("- Total Impressions: ").append(totalImpressions).append("\n");
        prompt.append("- Total Clicks: ").append(totalClicks).append("\n");
        prompt.append("- Total Conversions: ").append(totalConversions).append("\n");
        prompt.append("- Total Spend: $").append(String.format("%.2f", totalSpends)).append("\n");
        prompt.append("- Total Bids: ").append(totalBids).append("\n");
        prompt.append("- Average CTR: ").append(String.format("%.4f", avgCtr)).append("%\n");
        prompt.append("- Average Win Rate: ").append(String.format("%.4f", avgWinRate)).append("%\n");

        if (totalClicks > 0) {
            double conversionRate = (double) totalConversions / totalClicks * 100;
            prompt.append("- Conversion Rate: ").append(String.format("%.2f", conversionRate)).append("%\n");
        }

        if (totalConversions > 0 && totalSpends > 0) {
            double costPerConversion = totalSpends / totalConversions;
            prompt.append("- Cost Per Conversion: $").append(String.format("%.2f", costPerConversion)).append("\n");
        }

        prompt.append("\nDaily Breakdown:\n");
        for (CampaignData data : campaignData) {
            prompt.append("Date: ").append(data.getDate())
                    .append(" | Impressions: ").append(data.getImpressions())
                    .append(" | Clicks: ").append(data.getClicks())
                    .append(" | Conversions: ").append(data.getConversions())
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

    private String generateNoDataResponse(String campaignName, String startDate, String endDate) {
        String prompt = String.format(
                "Generate a helpful response for when no campaign data is found. " +
                        "Campaign: %s, Date Range: %s to %s. " +
                        "Explain that no data was found and suggest possible reasons (campaign might not exist, " +
                        "no activity in the specified period, etc.) and next steps.",
                campaignName, startDate, endDate
        );

        try {
            return ollamaService.generateAnalysis(prompt);
        } catch (Exception e) {
            return String.format("No data found for campaign '%s' between %s and %s. " +
                    "This could mean the campaign doesn't exist, had no activity during this period, " +
                    "or there might be an issue with the campaign name format. " +
                    "Please verify the campaign name and try again.", campaignName, startDate, endDate);
        }
    }
}
