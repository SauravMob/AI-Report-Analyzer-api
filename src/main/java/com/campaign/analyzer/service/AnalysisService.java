package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.CampaignData;
import com.campaign.analyzer.entity.CampaignReportResponse;
import com.campaign.analyzer.utils.DateUtils;
import com.campaign.analyzer.utils.EmptyDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnalysisService {

    @Autowired
    private CampaignApiService campaignApiService;

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private EmptyDataResponse emptyDataResponse;

    @Autowired
    private DateUtils dateUtils;

    public String analyzeQuery(String query, String bearerToken) {
        try {
            // Extract campaign name and date range from query
            String campaignName = campaignApiService.extractCampaignName(query);
            if (campaignName == null) {
                return "I couldn't identify a campaign name in your query.";
            }

            String[] dateRange = dateUtils.extractDateRange(query);
            String startDate = dateRange[0];
            String endDate = dateRange[1];

            // Fetch campaign data
            CampaignReportResponse reportResponse = campaignApiService.getCampaignReport(campaignName, startDate, endDate, bearerToken);

            if (reportResponse == null || reportResponse.getContent() == null || reportResponse.getContent().isEmpty()) {
                return emptyDataResponse.generateNoDataResponse(campaignName, startDate, endDate);
            }

            // Generate analysis prompt
            String analysisPrompt = campaignApiService.buildAnalysisPrompt(campaignName, reportResponse.getContent(), startDate, endDate, query);

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
}
