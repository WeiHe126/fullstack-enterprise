package com.company.backend.service.impl;

import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import com.company.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String username, String email) throws Exception {

        Optional<User> existUser = userRepository.findByEmail(email);

        if(existUser.isPresent())
            throw new Exception("User email already exist");

        return userRepository.save(new User(username, email));
    }

    @Override
    @Transactional
    public void testDirtyChecking() {
        User user = userRepository.findById(1L).orElseThrow();

        System.out.println("Before change: " + user.getName());

        user.setName("NuevoNombre");

        System.out.println("After change: " + user.getName());
    }

    @Override
    @Transactional
    public User loadUser() {
        return userRepository.findById(1L).orElseThrow();
    }

    @Override
    @Transactional
    public void testFlush() {
        User user = userRepository.findById(1L).orElseThrow();
        user.setName("X");

        userRepository.findAll(); // aquí Hibernate puede hacer flush antes
    }
}
