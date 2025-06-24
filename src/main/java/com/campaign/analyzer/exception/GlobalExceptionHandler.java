package com.campaign.analyzer.exception;

import com.campaign.analyzer.entity.AnalyzeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AnalyzeResponse> handleRuntimeException(RuntimeException e) {
        String errorMessage = e.getMessage();

        if (errorMessage.contains("Ollama service is not available")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new AnalyzeResponse("AI analysis service is currently unavailable. Please ensure Ollama is running."));
        }

        if (errorMessage.contains("Failed to fetch campaign report")) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new AnalyzeResponse("Unable to fetch campaign data. Please check the campaign API service."));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AnalyzeResponse("An unexpected error occurred: " + errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AnalyzeResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AnalyzeResponse("System error: " + e.getMessage()));
    }
}
