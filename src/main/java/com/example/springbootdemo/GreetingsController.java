package com.example.springbootdemo;

import com.example.springbootdemo.service.ChatResponseService;
import com.example.springbootdemo.model.ChatGptResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class GreetingsController {

    private final ChatResponseService chatResponseService;

    public GreetingsController(ChatResponseService service){
        this.chatResponseService = service;
    }

    @GetMapping("/greeting")
    public String greeting(Model model) {
        String response = chatResponseService.getAndLogResponse();

        List<ChatGptResponse> todayResponses = chatResponseService.getResponsesForToday();
        StringBuilder log = new StringBuilder("<br /><br />Today's saved responses:<br /><br />\n");
        todayResponses.forEach(r -> log.append("- ").append(r.getResponseText()).append("<br />\n"));

        model.addAttribute("latestResponse", response);
        model.addAttribute("responses", todayResponses);

        return "greeting";
        // return "Hello, Spring Boot! Here is the OpenAI response:\n<br />" + response + "\n\n<br /><br />" + log;
    }
}