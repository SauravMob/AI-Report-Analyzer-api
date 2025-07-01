package com.campaign.analyzer.controller;

import com.campaign.analyzer.entity.AnalyzeRequest;
import com.campaign.analyzer.entity.AnalyzeResponse;
import com.campaign.analyzer.enums.ReportType;
import com.campaign.analyzer.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaign")
@CrossOrigin("*")
public class CampaignController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping(value = "/analyze",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyzeResponse> analyzeCampaign(@RequestBody AnalyzeRequest request) {
        System.out.println("=== CONTROLLER START ===");
        System.out.println("Request received: " + request.getQuery());
        try {
            if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
                System.out.println("Empty query, returning bad request");
                AnalyzeResponse errorResponse = new AnalyzeResponse("Query cannot be empty");
                System.out.println("Error response created: " + errorResponse.toString());
                return ResponseEntity.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }

            System.out.println("Calling analysis service...");
            String analysis = analysisService.analyzeQuery(request.getQuery(), request.getBearerToken(), ReportType.CAMPAIGN);
            System.out.println("Analysis received, length: " + (analysis != null ? analysis.length() : "null"));

            // Check if analysis is null or empty
            if (analysis == null || analysis.trim().isEmpty()) {
                System.out.println("Analysis is null/empty, creating error response");
                AnalyzeResponse errorResponse = new AnalyzeResponse("Analysis could not be generated");
                return ResponseEntity.internalServerError()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }

            System.out.println("Creating success response...");
            AnalyzeResponse successResponse = new AnalyzeResponse(analysis, true);
            System.out.println("Success response created: " + successResponse.toString());

            System.out.println("Returning response...");
            ResponseEntity<AnalyzeResponse> responseEntity = ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(successResponse);
            System.out.println("=== CONTROLLER END SUCCESS ===");
            return responseEntity;

        } catch (Exception e) {
            System.err.println("=== CONTROLLER EXCEPTION ===");
            System.err.println("Exception in controller: " + e.getMessage());
            e.printStackTrace();

            try {
                AnalyzeResponse errorResponse = new AnalyzeResponse("Error processing campaign request: " + e.getMessage());
                System.out.println("Error response created: " + errorResponse.toString());
                return ResponseEntity.internalServerError()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            } catch (Exception e2) {
                System.err.println("Failed to create error response: " + e2.getMessage());
                e2.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Campaign Analysis Service is running");
    }
}