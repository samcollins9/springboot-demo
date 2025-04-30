package com.example.springbootdemo.client;

import com.example.springbootdemo.model.ChatGptResponse;
import java.util.List;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Component
public class DynamoDbClientWrapper {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<ChatGptResponse> responseTable;

    public DynamoDbClientWrapper() {
        DynamoDbClient client = DynamoDbClient.builder()
                .region(Region.US_EAST_1) // ✅ replace with your region
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        this.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();

        this.responseTable = enhancedClient.table("chatgpt_response_log",
                TableSchema.fromBean(ChatGptResponse.class));
    }

    public void saveResponse(ChatGptResponse response) {
        responseTable.putItem(PutItemEnhancedRequest.builder(ChatGptResponse.class)
                .item(response)
                .build());
    }

    public List<ChatGptResponse> getResponsesByDate(String datePrefix) {
        return responseTable.scan().items()
                .stream()
                .filter(item -> item.getResponseDate() != null && item.getResponseDate().startsWith(datePrefix))
                .toList();
    }

}
