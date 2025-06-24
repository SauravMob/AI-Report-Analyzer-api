package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.CampaignData;
import com.campaign.analyzer.entity.CampaignReportRequest;
import com.campaign.analyzer.entity.CampaignReportResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CampaignApiService {
    private final WebClient webClient;

    public CampaignApiService(@Value("${api.base-url.campaign}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public CampaignReportResponse getCampaignReport(String campaignName, String startDate, String endDate, String bearerToken) {
        System.out.println("getCampaignReport: " + campaignName + " : " + startDate + " : " + endDate);
        try {
            CampaignReportRequest request = new CampaignReportRequest(campaignName, startDate, endDate);
            return webClient.post()
                    .uri("/api/reports/campaign/v2?pageNo=0&pageSize=10&reportType=CUMULATIVE")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(CampaignReportResponse.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to fetch campaign report: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error calling campaign API: " + e.getMessage(), e);
        }
    }

    public String buildAnalysisPrompt(String campaignName, List<CampaignData> campaignData,
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

        avgCtr = !campaignData.isEmpty() ? avgCtr / campaignData.size() : 0;
        avgWinRate = !campaignData.isEmpty() ? avgWinRate / campaignData.size() : 0;

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

    public String extractCampaignName(String query) {
        // Pattern to match campaign names like "campaign 61", "campaign_61", "campaign61"
        Pattern campaignPattern = Pattern.compile("\\b(campaign[_\\s]*\\d+)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = campaignPattern.matcher(query);

        if (matcher.find()) {
            return matcher.group(1).replaceAll("[_\\s]+", ""); // Optional: remove underscores/spaces if needed
        }

        // Fallback: extract any campaign-like name (e.g., CampaignX123)
        Pattern genericPattern = Pattern.compile("\\b(campaign[_\\w]*\\d+)\\b", Pattern.CASE_INSENSITIVE);
        Matcher genericMatcher = genericPattern.matcher(query);

        if (genericMatcher.find()) {
            return genericMatcher.group(1);
        }

        return null;
    }
}
