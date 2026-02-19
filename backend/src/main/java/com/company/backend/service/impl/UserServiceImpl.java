package com.company.backend.service.impl;

import com.company.backend.exception.EmailAlreadyExistsException;
import com.company.backend.exception.UserNotFoundException;
import com.company.backend.model.dto.ChangeEmailRequestDto;
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
    public User createUser(String username, String email) {

        Optional<User> existUser = userRepository.findByEmail(email);

        if (existUser.isPresent())
            throw new EmailAlreadyExistsException(email);

        return userRepository.save(new User(username, email));
    }

    @Override
    @Transactional
    public void changeEmail(Long id, ChangeEmailRequestDto request) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        Optional<User> userWithEmail = userRepository.findByEmail(request.getEmail());

        if (userWithEmail.isPresent())
            throw new EmailAlreadyExistsException(request.getEmail());

        user.setEmail(request.getEmail());
    }
}
