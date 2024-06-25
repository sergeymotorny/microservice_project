package com.motorny.services;

import com.motorny.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUser();
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long id);
    String deleteUser(Long id);
}
