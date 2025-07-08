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
@RequestMapping("/api/creative")
@CrossOrigin("*")
public class CreativeController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping(value = "/analyze",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyzeResponse> analyzeCreative(@RequestBody AnalyzeRequest request) {
        try {
            if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
                AnalyzeResponse errorResponse = new AnalyzeResponse("Query cannot be empty");
                return ResponseEntity.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }

            String analysis = analysisService.analyzeQuery(request.getQuery(), request.getBearerToken(), ReportType.CREATIVE);
            if (analysis == null || analysis.trim().isEmpty()) {
                AnalyzeResponse errorResponse = new AnalyzeResponse("Analysis could not be generated");
                return ResponseEntity.internalServerError()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            }

            AnalyzeResponse successResponse = new AnalyzeResponse(analysis, true);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(successResponse);

        } catch (Exception e) {
            try {
                AnalyzeResponse errorResponse = new AnalyzeResponse("Error processing creatie request: " + e.getMessage());
                return ResponseEntity.internalServerError()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorResponse);
            } catch (Exception e2) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Creative Analysis Service is running");
    }
}