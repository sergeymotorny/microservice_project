package com.motorny.controllers;

import com.motorny.dto.UserDto;
import com.motorny.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        log.info("UserController: getAllUsers");
        return userService.getAllUser();
    }

    @PostMapping("/users")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("UserController: createUser");
        return userService.createUser(userDto);
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto,
                              @PathVariable("id") Long id) {
        return userService.updateUser(userDto, id);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
