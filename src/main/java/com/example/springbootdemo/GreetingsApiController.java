package com.example.springbootdemo;

import com.example.springbootdemo.model.ChatGptResponse;
import com.example.springbootdemo.model.GreetingApiResponse;
import com.example.springbootdemo.service.ChatResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GreetingsApiController {

    private final ChatResponseService chatResponseService;

    public GreetingsApiController(ChatResponseService chatResponseService) {
        this.chatResponseService = chatResponseService;
    }

    @GetMapping("/greeting")
    public GreetingApiResponse getGreetingApi() {
        String latest = chatResponseService.getAndLogResponse();
        List<ChatGptResponse> todayResponses = chatResponseService.getResponsesForToday();
        return new GreetingApiResponse(latest, todayResponses);
    }

    @PostMapping("/greeting")
    public GreetingApiResponse generateResponse(@RequestBody Map<String, Object> payload) {
        String reply = chatResponseService.generateAndLogResponse(payload);
        List<ChatGptResponse> todayResponses = chatResponseService.getResponsesForToday();
        return new GreetingApiResponse(reply, todayResponses);
    }

}
