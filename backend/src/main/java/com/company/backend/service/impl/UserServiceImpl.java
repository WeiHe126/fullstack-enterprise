package com.company.backend.service.impl;

import com.company.backend.model.entity.User;
import com.company.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> getAllUsers() {

        User user1 = new User ("1", "Name1", "mail1@mail.com");
        User user2 = new User ("2", "Name2", "mail2@mail.com");
        User user3 = new User ("3", "Name3", "mail3@mail.com");

        return List.of(user1, user2, user3);
    }
}
