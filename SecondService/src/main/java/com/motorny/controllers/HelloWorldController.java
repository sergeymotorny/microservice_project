package com.motorny.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping("/hello-world")
    public String getHelloWorld() {
        return "hello world from SecondService!";
    }
}
