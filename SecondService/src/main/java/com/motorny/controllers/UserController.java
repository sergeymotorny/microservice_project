package com.motorny.controllers;

import com.motorny.models.User;
import com.motorny.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable("userId") Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/hello-world")
    public String getHelloWorld() {
        return "Hello World from SecondService";
    }
}
