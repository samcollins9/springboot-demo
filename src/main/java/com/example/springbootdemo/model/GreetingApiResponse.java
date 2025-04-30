package com.example.springbootdemo.model;

import java.util.List;

public class GreetingApiResponse {
    private String latestResponse;
    private List<ChatGptResponse> allResponsesToday;

    public GreetingApiResponse(String latestResponse, List<ChatGptResponse> allResponsesToday) {
        this.latestResponse = latestResponse;
        this.allResponsesToday = allResponsesToday;
    }

    public String getLatestResponse() {
        return latestResponse;
    }

    public void setLatestResponse(String latestResponse) {
        this.latestResponse = latestResponse;
    }

    public List<ChatGptResponse> getAllResponsesToday() {
        return allResponsesToday;
    }

    public void setAllResponsesToday(List<ChatGptResponse> allResponsesToday) {
        this.allResponsesToday = allResponsesToday;
    }
}
