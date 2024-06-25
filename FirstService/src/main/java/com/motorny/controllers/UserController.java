package com.motorny.controllers;

import com.motorny.dto.UserDto;
import com.motorny.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers(@RequestHeader() String auth) {
        return userService.getAllUsers(auth);
    }
}
