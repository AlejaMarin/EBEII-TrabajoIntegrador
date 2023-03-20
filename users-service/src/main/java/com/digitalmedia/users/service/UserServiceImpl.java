package com.digitalmedia.users.service;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.repository.KeycloakRepository;
import com.digitalmedia.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final KeycloakRepository keycloakRepository;

    @Override
    public void createUser(User user){

        keycloakRepository.createUser(user);
    }

    @Override
    public User validateAndGetUserExtra(String username) {

        return userRepository.validateAndGetUser(username);
    }

    @Override
    public Optional<User> getUserExtra(String username) {

        return userRepository.getUserExtra(username);
    }

    @Override
    public User saveUserExtra(User user) {

        return userRepository.saveUserExtra(user);
    }

    @Override
    public List<User> getUsers() {

        return userRepository.getUsers();

    }

    @Override
    public List<User> findByUsername(String username) {

        return keycloakRepository.findByUsername(username);
    }

    @Override
    public List<User> findByFirstName(String name) {

        return keycloakRepository.findByFirstName(name);
    }

    @Override
    public Optional<User> findById(String id) {

        return keycloakRepository.findById(id);
    }

    @Override
    public List<User> findUsersNoAdmin() {

        return keycloakRepository.findUsersNoAdmin();
    }

    @Override
    public List<User> findByUsernamePublic(String username) {

        return keycloakRepository.findByUsernamePublic(username);
    }
}
