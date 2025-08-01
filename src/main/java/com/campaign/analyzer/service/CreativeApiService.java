package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.CreativeData;
import com.campaign.analyzer.entity.CreativeReportRequest;
import com.campaign.analyzer.entity.ReportResponse;
import com.campaign.analyzer.enums.ReportType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreativeApiService implements ReportApiService<CreativeData> {
    private final WebClient webClient;

    public CreativeApiService(@Value("${api.base-url.creative}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public ReportResponse<CreativeData> getReport(String creativeName, String startDate, String endDate, String interval, String bearerToken) {
        System.out.println("getCreativeReport: " + creativeName + " : " + startDate + " : " + endDate);
        try {
            CreativeReportRequest request = new CreativeReportRequest();
            request.setCreativeName(creativeName);
            request.setStartDate(startDate);
            request.setInterval("CUSTOM");
            request.setEndDate(endDate);
            request.setReportType("DATEWISE");

            return webClient.post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ReportResponse<CreativeData>>() {
                    })
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Creative API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null; // Return null instead of throwing exception to enable fallback
        } catch (Exception e) {
            System.err.println("Creative API connection error: " + e.getMessage());
            return null; // Return null instead of throwing exception to enable fallback
        }
    }

    @Override
    public String extractName(String query) {
        Pattern pattern = Pattern.compile("creative:\\s*([^\\s]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        return matcher.find() ? matcher.group(1) : null;
    }

    @Override
    public ReportType getReportType() {
        return ReportType.CREATIVE;
    }
}
