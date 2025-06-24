package com.campaign.analyzer.utils;

import com.campaign.analyzer.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmptyDataResponse {

    @Autowired
    private OllamaService ollamaService;

    public String generateNoDataResponse(String campaignName, String startDate, String endDate) {
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
