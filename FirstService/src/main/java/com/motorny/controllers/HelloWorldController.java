package com.motorny.controllers;

import com.motorny.client.FirstServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private final FirstServiceClient firstServiceClient;

    public HelloWorldController(FirstServiceClient firstServiceClient) {
        this.firstServiceClient = firstServiceClient;
    }

    @GetMapping("/api/hello-world")
    public String getHelloWorld(@RequestHeader("auth") String authHeader) {
        return "FirstService a calling: " + firstServiceClient.getHelloWorld(authHeader);
    }
}
