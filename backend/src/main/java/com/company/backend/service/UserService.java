package com.company.backend.service;

import com.company.backend.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User createUser(String username, String email) throws Exception;
}
