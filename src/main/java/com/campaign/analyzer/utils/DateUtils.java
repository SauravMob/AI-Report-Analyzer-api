package com.campaign.analyzer.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static String[] getLastWeekRange() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(7);
        LocalDate endDate = today.minusDays(1);
        return new String[]{formatDate(startDate), formatDate(endDate)};
    }

    public static String[] getThisWeekRange() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(today.getDayOfWeek().getValue() - 1);
        return new String[]{formatDate(startDate), formatDate(today)};
    }

    public static String[] getLastMonthRange() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());
        return new String[]{formatDate(startDate), formatDate(endDate)};
    }

    public static String[] getThisMonthRange() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1);
        return new String[]{formatDate(startDate), formatDate(today)};
    }

}
