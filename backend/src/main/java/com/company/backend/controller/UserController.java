package com.company.backend.controller;

import com.company.backend.model.dto.UserResponseDto;
import com.company.backend.model.entity.User;
import com.company.backend.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<UserResponseDto> listUsers() {
        List<User> users = userService.getAllUsers();

        return users.stream().map(u -> new UserResponseDto(u.getId(), u.getName())).toList();
    }

    @PostMapping
    UserResponseDto createUser(@NotNull String username, @NotNull String email) {

        User user = userService.createUser(username, email);

        return new UserResponseDto(user.getId(), user.getName());
    }
}
