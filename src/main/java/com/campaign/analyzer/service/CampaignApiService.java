package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.CampaignData;
import com.campaign.analyzer.entity.CampaignReportRequest;
import com.campaign.analyzer.entity.CampaignReportResponse;
import com.campaign.analyzer.entity.ReportResponse;
import com.campaign.analyzer.enums.ReportType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CampaignApiService implements ReportApiService<CampaignData> {
    private final WebClient webClient;

    public CampaignApiService(@Value("${api.base-url.campaign}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public ReportResponse<CampaignData> getReport(String campaignName, String startDate, String endDate, String bearerToken) {
        System.out.println("getCampaignReport: " + campaignName + " : " + startDate + " : " + endDate);
        try {
            CampaignReportRequest request = new CampaignReportRequest(campaignName, startDate, endDate);
            return webClient.post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerToken)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ReportResponse<CampaignData>>() {
                    })
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to fetch campaign report: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error calling campaign API: " + e.getMessage(), e);
        }
    }

    @Override
    public String extractName(String query) {
        Pattern pattern = Pattern.compile("campaign:\\s*([^\\s]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        return matcher.find() ? matcher.group(1) : null;
    }

    @Override
    public ReportType getReportType() {
        return ReportType.CAMPAIGN;
    }
}
