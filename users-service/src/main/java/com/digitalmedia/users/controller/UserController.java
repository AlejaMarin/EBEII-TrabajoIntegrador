package com.digitalmedia.users.controller;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.model.dto.UserRequest;
import com.digitalmedia.users.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    @PreAuthorize("hasAuthority('GROUP_admin')")
    @GetMapping("/admin/{username}")
    public List<User> findByUsername(@PathVariable String username) {

        return userService.findByUsername(username);
    }

    //@PreAuthorize("hasAuthority('GROUP_client')")
    @GetMapping("/public/{username}")
    public List<User> findByUsernamePublic(@PathVariable String username) {

        return userService.findByUsernamePublic(username);
    }

    @GetMapping("/firstname/{name}")
    public List<User> findByFirstName(@PathVariable String name) {

        return userService.findByFirstName(name);
    }

    @PreAuthorize("hasAuthority('GROUP_admin')")
    @GetMapping("/admin/id/{id}")
    public Optional<User> findById(@PathVariable String id) {

        return userService.findById(id);
    }

    @PreAuthorize("hasAuthority('GROUP_admin')")
    @GetMapping("/admin")
    public List<User> findUsersNoAdmin() {

        return userService.findUsersNoAdmin();
    }

    @PreAuthorize("hasAuthority('GROUP_admin')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {

        return userService.saveUserExtra(user);
    }

    @PreAuthorize("hasAuthority('GROUP_admin')")
    @GetMapping
    public List<User> getMongoUsers() {

        return userService.getUsers();
    }

    @PreAuthorize("hasAuthority('GROUP_admin')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void createKeycloakUser(@Valid @RequestBody User user) {

        userService.createUser(user);
    }

    //TODO  estos dos endpoints funcionaran cuando este configurada la seguridad en el proyecto

    /*@GetMapping("/me")
    public User getUserExtra(Principal principal) {
        return userService.validateAndGetUserExtra(principal.getName());
    }*/

    @GetMapping("/me")
    public User getUserExtra(@RequestParam String principal) {

        return userService.validateAndGetUserExtra(principal);
    }

    @PostMapping("/me")
    public User saveUserExtra(@Valid @RequestBody UserRequest updateUserRequest, @RequestParam(value = "principal") String principal) {

        Optional<User> userOptional = userService.getUserExtra(principal);
        User userExtra = userOptional.orElseGet(() -> new User(principal));
        userExtra.setAvatar(updateUserRequest.getAvatar());
        return userService.saveUserExtra(userExtra);
    }

}
