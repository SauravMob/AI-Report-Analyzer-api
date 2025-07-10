package com.campaign.analyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityExtractionService {
    @Autowired
    private OllamaService ollamaService;

    public String extractEntityName(String query) {
        String extractionPrompt = buildExtractionPrompt(query);
        try {
            String response = ollamaService.generateAnalysis(extractionPrompt);
            return cleanResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract entity name: " + e.getMessage(), e);
        }
    }

    private String buildExtractionPrompt(String query) {
        return """
                You are an expert at extracting campaign and creative names from user queries about digital marketing analysis.
                
                Your task: Extract ONLY the campaign or creative name from the following query. Return ONLY the name, nothing else.
                
                Rules for extraction:
                1. Look for names after keywords like: "for", "of", "on", "analyze", "analysis", "campaign", "creative"
                2. Entity names are typically:
                   - Alphanumeric with underscores, hyphens, or spaces
                   - May contain dimensions (320x480, 9x16)
                   - May contain durations (20 SEC, 30s)
                   - May contain language codes (HINDI, KANNADA)
                   - May contain platform indicators (Mob, Web, CTV)
                3. Exclude time periods like "today", "yesterday", "last week", "this month"
                4. Exclude common words like "give", "me", "analysis", "report", "generate"
                
                Examples:
                Query: "Give me analysis for campaign Swiggy_RT_IN_AOS_Video_AO_D5_Open for today"
                Response: Swiggy_RT_IN_AOS_Video_AO_D5_Open
                
                Query: "Analyze creative 6288100320x480_2 for yesterday"
                Response: 6288100320x480_2
                
                Query: "Show performance of Zepto_SKAN_Inmobi_Video_OS_Freq_1by6_Timings_open_320x480 last week"
                Response: Zepto_SKAN_Inmobi_Video_OS_Freq_1by6_Timings_open_320x480
                
                Query: "Generate report analysis for Dostt_Telugu_IN_AOS for this month"
                Response: Dostt_Telugu_IN_AOS
                
                Query: "How is Kotak_Migration_FD_2_320x50 performing last week"
                Response: Kotak_Migration_FD_2_320x50
                
                Now extract the entity name from this query:
                Query: """ + query + """
                
                Response:""";
    }

    private String cleanResponse(String response) {
        if (response == null) return null;

        String cleaned = response.replaceAll("(?s)<think>.*?</think>", "") // Remove think blocks across lines
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1") // Remove markdown bold
                .replaceAll("\\n-", "\n•") // Convert dashes to bullets
                .replaceAll("\\n{3,}", "\n\n") // Clean extra newlines
                .trim();

        if (cleaned.isEmpty() ||
                cleaned.matches("(?i).*(analysis|report|generate|show|performance).*") ||
                cleaned.matches("(?i)^(today|yesterday|last\\s+week|this\\s+week|last\\s+month|this\\s+month)$")) {
            return null;
        }

        return cleaned;
    }
}
