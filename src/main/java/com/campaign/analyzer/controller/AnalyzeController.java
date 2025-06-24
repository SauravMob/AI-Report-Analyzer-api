package com.campaign.analyzer.controller;

import com.campaign.analyzer.entity.AnalyzeRequest;
import com.campaign.analyzer.entity.AnalyzeResponse;
import com.campaign.analyzer.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AnalyzeController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping("/analyze")
    public ResponseEntity<AnalyzeResponse> analyzeCampaign(@RequestBody AnalyzeRequest request) {
        try {
            if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new AnalyzeResponse("Query cannot be empty"));
            }

            String analysis = analysisService.analyzeQuery(request.getQuery(), request.getBearerToken());
            return ResponseEntity.ok(new AnalyzeResponse(analysis, true));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new AnalyzeResponse("Error processing request: " + e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Campaign RAG Analysis Service is running");
    }
}