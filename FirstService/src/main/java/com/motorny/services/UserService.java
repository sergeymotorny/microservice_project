package com.motorny.services;

import com.motorny.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(String auth);
}
