package com.motorny.services.impl;

import com.motorny.models.User;
import com.motorny.repositories.UserRepository;
import com.motorny.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return List.of();
    }

    @Override
    public User getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.orElse(null);
    }

    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long id) {
        return null;
    }

    @Override
    public String deleteUser(Long id) {
        return "";
    }
}
