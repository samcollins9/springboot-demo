package com.example.springbootdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication(scanBasePackages = "com.example.springbootdemo")
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner showMappings(RequestMappingHandlerMapping mapping) {
        return args -> {
            mapping.getHandlerMethods().forEach((key, value) ->
                System.out.println("Mapped endpoint: " + key)
            );
        };
    }
}
