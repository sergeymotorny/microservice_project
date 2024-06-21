package com.motorny.services.impl;

import com.motorny.dto.UserDto;
import com.motorny.exceptions.UserNotFoundException;
import com.motorny.mappers.UserMapper;
import com.motorny.models.User;
import com.motorny.repositories.UserRepository;
import com.motorny.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with " + id + " was not found!"));

        foundUser.setFullName(userDto.getFullName());
        foundUser.setAge(userDto.getAge());

        User updateUser = userRepository.save(foundUser);

        return userMapper.toUserDto(updateUser);
    }

    @Transactional
    @Override
    public String deleteUser(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with " + id + " was not found!"));

        userRepository.delete(foundUser);

        return "User with " + id + " successfully deleted!";
    }
}
