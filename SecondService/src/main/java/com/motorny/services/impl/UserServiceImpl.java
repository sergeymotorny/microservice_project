package com.motorny.services.impl;

import com.motorny.dto.UserDto;
import com.motorny.exceptions.UserNotFoundException;
import com.motorny.mappers.UserMapper;
import com.motorny.models.User;
import com.motorny.repositories.UserRepository;
import com.motorny.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "users")
    public List<UserDto> getAllUser() {
        log.info("UserServiceImpl: getAllUser");
        return userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "foundUser", allEntries = true)
    })
    public UserDto createUser(UserDto userDto) {
        log.info("UserServiceImpl: createUser, '{}'", userDto.getFullName());
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id '" + id + "' was not found!"));

        foundUser.setFullName(userDto.getFullName());
        foundUser.setAge(userDto.getAge());

        User updateUser = userRepository.save(foundUser);

        return userMapper.toUserDto(updateUser);
    }

    @Transactional
    @Override
    public String deleteUser(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id '" + id + "' was not found!"));

        userRepository.delete(foundUser);

        return "User with id '" + id + "' successfully deleted!";
    }
}
