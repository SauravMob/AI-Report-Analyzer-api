package com.campaign.analyzer.controller;

import com.campaign.analyzer.entity.AnalyzeRequest;
import com.campaign.analyzer.entity.AnalyzeResponse;
import com.campaign.analyzer.enums.ReportType;
import com.campaign.analyzer.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/creative")
@CrossOrigin("*")
public class CreativeController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping("/analyze")
    public ResponseEntity<AnalyzeResponse> analyzeCreative(@RequestBody AnalyzeRequest request) {
        try {
            if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new AnalyzeResponse("Query cannot be empty"));
            }

            String analysis = analysisService.analyzeQuery(request.getQuery(), request.getBearerToken(), ReportType.CREATIVE);
            return ResponseEntity.ok(new AnalyzeResponse(analysis, true));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new AnalyzeResponse("Error processing creative request: " + e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Creative Analysis Service is running");
    }
}