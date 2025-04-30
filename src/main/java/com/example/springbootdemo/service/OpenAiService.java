package com.example.springbootdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private final WebClient webClient;

    public OpenAiService(@Value("${OPENAI_KEY}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public String getSampleResponse() {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[]{
                        Map.of("role", "user", "content", "Provide a random short fact less than 100 characters long.")
                }
        );

        try {
            // Synchronous (blocking) call for simplicity
            return webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> {
                        var choices = (java.util.List<Map<String, Object>>) response.get("choices");
                        if (choices != null && !choices.isEmpty()) {
                            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                            return message.get("content").toString().trim();
                        }
                        return "No response received";
                    })
                    .block(); // block to get the result synchronously
        } catch (Exception e) {
            return "Error calling OpenAI: " + e.getMessage();
        }
    }

    public String getResponseWithParameters(Map<String, Object> params) {
        String model = (String) params.getOrDefault("model", "gpt-3.5-turbo");
        String prompt = (String) params.getOrDefault("prompt", "Provide a random fun fact.");
        double temperature = ((Number) params.getOrDefault("temperature", 0.7)).doubleValue();
        int maxTokens = ((Number) params.getOrDefault("max_tokens", 150)).intValue();
        double frequencyPenalty = ((Number) params.getOrDefault("frequency_penalty", 0.0)).doubleValue();

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                },
                "temperature", temperature,
                "max_tokens", maxTokens,
                "frequency_penalty", frequencyPenalty
        );

        try {
            return webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> {
                        var choices = (List<Map<String, Object>>) response.get("choices");
                        if (choices != null && !choices.isEmpty()) {
                            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                            return message.get("content").toString().trim();
                        }
                        return "No response received";
                    })
                    .block();
        } catch (Exception e) {
            return "Error calling OpenAI: " + e.getMessage();
        }
    }


}
