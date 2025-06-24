package com.campaign.analyzer.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
