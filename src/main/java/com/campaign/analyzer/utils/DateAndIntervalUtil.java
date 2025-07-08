package com.campaign.analyzer.utils;

import com.campaign.analyzer.enums.TimeIntervals;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateAndIntervalUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String[] extractDateRange(String query) {
        String queryLower = query.toLowerCase();
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;
        TimeIntervals interval;

        if (queryLower.contains("today") && (queryLower.contains("yesterday") || queryLower.contains("and yesterday"))) {
            startDate = today.minusDays(1);
            endDate = today;
            interval = TimeIntervals.TODAY_YESTERDAY;
        } else if (queryLower.contains("today")) {
            startDate = today;
            endDate = today;
            interval = TimeIntervals.TODAY;
        } else if (queryLower.contains("yesterday")) {
            startDate = today.minusDays(1);
            endDate = today.minusDays(1);
            interval = TimeIntervals.YESTERDAY;
        } else if (queryLower.contains("this week") || queryLower.contains("thisweek")) {
            startDate = today.minusDays(today.getDayOfWeek().getValue() - 1);
            endDate = today;
            interval = TimeIntervals.THIS_WEEK;
        } else if (queryLower.contains("last week") || queryLower.contains("lastweek")) {
            startDate = today.minusDays(7);
            endDate = today.minusDays(1);
            interval = TimeIntervals.LAST_WEEK;
        } else if (queryLower.contains("last 7 days") || queryLower.contains("past 7 days") ||
                queryLower.contains("last seven days") || queryLower.contains("past seven days")) {
            startDate = today.minusDays(7);
            endDate = today.minusDays(1);
            interval = TimeIntervals.LAST_SEVEN_DAYS;
        } else if (queryLower.contains("this month") || queryLower.contains("thismonth")) {
            startDate = today.withDayOfMonth(1);
            endDate = today;
            interval = TimeIntervals.THIS_MONTH;
        } else if (queryLower.contains("last month") || queryLower.contains("lastmonth")) {
            startDate = today.minusMonths(1).withDayOfMonth(1);
            endDate = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());
            interval = TimeIntervals.LAST_MONTH;
        } else {
            // Default to last 7 days
            startDate = today.minusDays(7);
            endDate = today.minusDays(1);
            interval = TimeIntervals.LAST_SEVEN_DAYS;
        }

        String[] dateRange = new String[]{
                startDate.format(DATE_FORMATTER),
                endDate.format(DATE_FORMATTER)
        };

        return new String[]{
                startDate.format(DATE_FORMATTER),
                endDate.format(DATE_FORMATTER),
                interval.toString()
        };
    }
}
