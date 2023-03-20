package com.digitalmedia.users.service;

import com.digitalmedia.users.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void createUser(User user);

    User validateAndGetUserExtra(String username);

    Optional<User> getUserExtra(String username);

    User saveUserExtra(User userExtra);

    List<User> getUsers();

    List<User> findByUsername(String username);

    List<User> findByFirstName(String name);

    Optional<User> findById(String id);

    List<User> findUsersNoAdmin();

    List<User> findByUsernamePublic(String username);

}
