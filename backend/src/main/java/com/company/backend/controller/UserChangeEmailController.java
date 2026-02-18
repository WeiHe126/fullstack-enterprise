package com.company.backend.controller;

import com.company.backend.model.dto.ChangeEmailRequestDto;
import com.company.backend.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/{id}/change-email")
public class UserChangeEmailController {

    private final UserService userService;

    public UserChangeEmailController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserEmail(@NotNull @PathVariable Long id, @RequestBody ChangeEmailRequestDto newEmail) {
        userService.changeEmail(id, newEmail);
    }
}
