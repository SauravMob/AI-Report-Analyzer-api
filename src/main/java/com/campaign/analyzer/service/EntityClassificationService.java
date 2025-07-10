package com.campaign.analyzer.service;

import com.campaign.analyzer.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityClassificationService {

    @Autowired
    private OllamaService ollamaService;

    public ReportType classifyEntity(String entityName, String originalQuery) {
        String classificationPrompt = buildClassificationPrompt(entityName, originalQuery);
        try {
            String response = ollamaService.generateAnalysis(classificationPrompt);
            return parseClassificationResponse(response);
        } catch (Exception e) {
            // Fallback to rule-based classification if LLM fails
            return classifyEntityRuleBased(entityName, originalQuery);
        }
    }

    private String buildClassificationPrompt(String entityName, String originalQuery) {
        return """
                You are an expert at classifying digital marketing entities as either CAMPAIGN or CREATIVE.
               \s
                Your task: Classify the given entity as either "CAMPAIGN" or "CREATIVE" based on the rules below.
               \s
                Classification Rules:
               \s
                Classify as CREATIVE if ANY of these conditions are met:
                1. The original query explicitly contains the word "creative"
                2. The entity name contains video dimensions or aspect ratios like:
                   - "320x480", "6288100320x480", "9x16", "320x50", "1080x1920", "16x9", etc.
                3. The name includes duration indicators like:
                   - "20 SEC", "30s", "15 seconds", "1min", etc.
                4. The name contains typical creative metadata like:
                   - Languages: "HINDI", "KANNADA", "TELUGU", "ENGLISH", "TAMIL", etc.
                   - Quality markers: "HD", "4K", "FHD"
                   - Platform tags: "Mob", "Web", "CTV", "Mobile", "Desktop"
                   - Format indicators: "Banner", "Video", "Display"
               \s
                Otherwise, classify as CAMPAIGN.
               \s
                Examples:
                Entity: "Swiggy_RT_IN_AOS_Video_AO_D5_Open" → CAMPAIGN
                Entity: "6288100320x480_2" → CREATIVE (contains dimensions)
                Entity: "Zepto_SKAN_Inmobi_Video_OS_Freq_1by6_Timings_open_320x480" → CREATIVE (contains dimensions)
                Entity: "Dostt_Telugu_IN_AOS" → CREATIVE (contains language)
                Entity: "Kotak_Migration_FD_2_320x50" → CREATIVE (contains dimensions)
                Entity: "KKCL_Awareness_ctv_video_odisha" → CAMPAIGN
                Entity: "Blinkit_Video_Magnum_9x16" → CREATIVE (contains dimensions)
                Entity: "AN-KESARI HOLI-20 SEC-KANNADA-HD-Ent-Mob" → CREATIVE (duration, language, quality, platform)
               \s
                Now classify this entity:
                Original Query:\s""" + originalQuery + """
                Entity Name:\s""" + entityName + """
                
                Response (only "CAMPAIGN" or "CREATIVE"):""";
    }

    private ReportType parseClassificationResponse(String response) {
        if (response == null) return ReportType.CAMPAIGN;

        String cleaned = response.trim()
                .replaceAll("<think>.*?</think>", "")
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1")
                .toUpperCase();

        if (cleaned.contains("CREATIVE")) {
            return ReportType.CREATIVE;
        } else if (cleaned.contains("CAMPAIGN")) {
            return ReportType.CAMPAIGN;
        }

        // Default fallback
        return ReportType.CAMPAIGN;
    }

    // Fallback rule-based classification
    private ReportType classifyEntityRuleBased(String entityName, String originalQuery) {
        if (entityName == null) return ReportType.CAMPAIGN;

        String queryLower = originalQuery.toLowerCase();
        String nameLower = entityName.toLowerCase();

        // Check if query explicitly mentions creative
        if (queryLower.contains("creative")) {
            return ReportType.CREATIVE;
        }

        // Check for dimensions/aspect ratios
        if (nameLower.matches(".*\\d+x\\d+.*")) {
            return ReportType.CREATIVE;
        }

        // Check for duration indicators
        if (nameLower.matches(".*(\\d+\\s*sec|\\d+s|\\d+\\s*seconds|\\d+\\s*min|\\d+\\s*minutes).*")) {
            return ReportType.CREATIVE;
        }

        // Check for language indicators
        String[] languages = {"hindi", "kannada", "telugu", "english", "tamil", "bengali", "gujarati", "marathi"};
        for (String lang : languages) {
            if (nameLower.contains(lang)) {
                return ReportType.CREATIVE;
            }
        }

        // Check for quality/platform indicators
        String[] creativeIndicators = {"hd", "4k", "fhd", "mob", "web", "ctv", "mobile", "desktop", "banner"};
        for (String indicator : creativeIndicators) {
            if (nameLower.contains(indicator)) {
                return ReportType.CREATIVE;
            }
        }

        // Default to campaign
        return ReportType.CAMPAIGN;
    }
}