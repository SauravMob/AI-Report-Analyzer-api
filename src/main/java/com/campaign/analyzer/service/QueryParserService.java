package com.campaign.analyzer.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QueryParserService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    public String[] extractDateRange(String query) {
        String queryLower = query.toLowerCase();
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        if (queryLower.contains("today")) {
            startDate = today;
            endDate = today;
        } else if (queryLower.contains("yesterday")) {
            startDate = today.minusDays(1);
            endDate = today.minusDays(1);
        } else if (queryLower.contains("last week") || queryLower.contains("lastweek")) {
            startDate = today.minusDays(7);
            endDate = today.minusDays(1);
        } else if (queryLower.contains("this week") || queryLower.contains("thisweek")) {
            startDate = today.minusDays(today.getDayOfWeek().getValue() - 1);
            endDate = today;
        } else if (queryLower.contains("last month") || queryLower.contains("lastmonth")) {
            startDate = today.minusMonths(1).withDayOfMonth(1);
            endDate = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());
        } else if (queryLower.contains("this month") || queryLower.contains("thismonth")) {
            startDate = today.withDayOfMonth(1);
            endDate = today;
        } else {
            // Default to last 7 days
            startDate = today.minusDays(7);
            endDate = today.minusDays(1);
        }

        return new String[]{
                startDate.format(DATE_FORMATTER),
                endDate.format(DATE_FORMATTER)
        };
    }
}
