package com.example.springbootdemo.service;

import com.example.springbootdemo.client.DynamoDbClientWrapper;
import com.example.springbootdemo.model.ChatGptResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;

@Service
public class ChatResponseService {

    private final OpenAiService openAiService;
    private final DynamoDbClientWrapper dynamoDbClient;

    private final AtomicLong responseCounter = new AtomicLong(1000);

    public ChatResponseService(OpenAiService openAiService, DynamoDbClientWrapper dynamoDbClient) {
        this.openAiService = openAiService;
        this.dynamoDbClient = dynamoDbClient;
    }

    public String getAndLogResponse() {
        String openAiReply = openAiService.getSampleResponse();

        ChatGptResponse response = new ChatGptResponse();
        response.setResponseId(responseCounter.incrementAndGet());
        response.setResponseDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        response.setResponseText(openAiReply);

        dynamoDbClient.saveResponse(response);

        return openAiReply;
    }

    public List<ChatGptResponse> getResponsesForToday() {
        String todayPrefix = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()); // e.g. "2025-04-15"
        return dynamoDbClient.getResponsesByDate(todayPrefix);
    }

    public String generateAndLogResponse(Map<String, Object> promptParams) {
        String reply = openAiService.getResponseWithParameters(promptParams);

        ChatGptResponse response = new ChatGptResponse();
        response.setResponseId(responseCounter.incrementAndGet());
        response.setResponseDate(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        response.setResponseText(reply);

        dynamoDbClient.saveResponse(response);
        return reply;
    }



}
