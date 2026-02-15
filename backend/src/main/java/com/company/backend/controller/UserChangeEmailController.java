package com.company.backend.controller;

import com.company.backend.model.dto.ChangeEmailRequestDto;
import com.company.backend.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{id}/change-email")
public class UserChangeEmailController {

    private final UserService userService;

    public UserChangeEmailController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUserEmail(@NotNull @PathVariable Long id, @RequestBody ChangeEmailRequestDto newEmail){
        userService.changeEmail(id, newEmail);
    }
}
