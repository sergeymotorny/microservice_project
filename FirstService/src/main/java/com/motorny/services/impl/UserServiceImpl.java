package com.motorny.services.impl;

import com.motorny.client.FirstServiceClient;
import com.motorny.dto.UserDto;
import com.motorny.dto.UserDtoFromSecondService;
import com.motorny.mappers.UserMapper;
import com.motorny.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final FirstServiceClient firstServiceClient;

    @Autowired
    public UserServiceImpl(FirstServiceClient firstServiceClient, UserMapper userMapper) {
        this.firstServiceClient = firstServiceClient;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers(String auth) {

        List<UserDtoFromSecondService> allUsers = firstServiceClient.getAllUsers(auth);

        return allUsers.stream()
                .map(userMapper::toUserDto)
                .toList();
    }
}
