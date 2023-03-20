package com.digitalmedia.users.repository;

import com.digitalmedia.users.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class KeycloakRepository {

    @Autowired
    private Keycloak keycloak;
    @Value("DigitalMedia")
    private String realm;

    public void createUser(User newUser){

        UserRepresentation user = new UserRepresentation();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEnabled(true);
        CredentialRepresentation password = new CredentialRepresentation();
        password.setTemporary(false);
        password.setType(CredentialRepresentation.PASSWORD);
        password.setValue("digitalmedia");
        user.setCredentials(Collections.singletonList(password));
        keycloak.realm(realm).users().create(user);

    }

    public List<User> findByUsername(String username) {

        List<UserRepresentation> user = keycloak.realm(realm).users().search(username);
        return user.stream().map(this::toUser).collect(Collectors.toList());
    }

    public List<User> findByUsernamePublic(String username) {

        List<UserRepresentation> user = keycloak.realm(realm).users().search(username);
        return user.stream().map(this::toPublicUser).collect(Collectors.toList());
    }

    public List<User> findByFirstName(String name) {

        List<UserRepresentation> users = keycloak.realm(realm).users().search(name);
        return users.stream().map(this::toUser).collect(Collectors.toList());
    }

    public Optional<User> findById(String id) {

        UserRepresentation user = keycloak.realm(realm).users().get(id).toRepresentation();
        return Optional.of(toUser(user));
    }

    public List<User> findUsersNoAdmin() {

        List<UserRepresentation> users = keycloak.realm(realm).users().list();
        List<UserRepresentation> usersNoAdmin = users.stream().filter(userRepresentation -> {
            List<GroupRepresentation> groups = keycloak.realm(realm).users().get(userRepresentation.getId()).groups();
            return groups.stream().noneMatch(s -> Objects.equals(s.getName(), "admin"));
        }).collect(Collectors.toList());
        return usersNoAdmin.stream().map(this::toNoAdminUser).collect(Collectors.toList());
    }

    private User toUser(UserRepresentation userRepresentation) {

        return new User(userRepresentation.getId(), userRepresentation.getUsername(), userRepresentation.getEmail(), userRepresentation.getFirstName(), userRepresentation.getLastName());
    }

    private User toNoAdminUser(UserRepresentation userRepresentation) {

        return new User(userRepresentation.getUsername(), userRepresentation.getEmail());
    }

    private User toPublicUser(UserRepresentation userRepresentation) {

        return new User(userRepresentation.getUsername(), userRepresentation.getEmail(), userRepresentation.getFirstName(), userRepresentation.getLastName());
    }
}
