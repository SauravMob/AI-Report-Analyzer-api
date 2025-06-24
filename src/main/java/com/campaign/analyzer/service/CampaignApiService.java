package com.campaign.analyzer.service;

import com.campaign.analyzer.entity.CampaignReportRequest;
import com.campaign.analyzer.entity.CampaignReportResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class CampaignApiService {
    private final WebClient webClient;

    public CampaignApiService(@Value("${campaign.api.base-url}") String baseUrl) {
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

}
