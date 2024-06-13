package com.motorny.controllers;

import com.motorny.client.UserClient;
import com.motorny.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserClient userClient;

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/user/{userId}")
    public String getUserId(@PathVariable("userId") Long userId) {
        User user = userClient.getUserById(userId);
        return "First service: " + user.getUsername();
    }

    @GetMapping("/hello-world")
    public String getHelloWorld() {
        return "FirstService a calling: " + userClient.getHelloWorld();
    }
}
