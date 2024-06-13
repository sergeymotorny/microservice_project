package com.motorny.services;

import com.motorny.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User getUser(Long id);

    User createUser(User user);

    User updateUser(User user, Long id);

    String deleteUser(Long id);
}
