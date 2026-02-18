package com.company.backend.service;

import com.company.backend.exception.EmailAlreadyExistsException;
import com.company.backend.exception.UserNotFoundException;
import com.company.backend.model.dto.ChangeEmailRequestDto;
import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import com.company.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private PodamFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new PodamFactoryImpl();
    }

    @Test
    void testGetAllUsers() {

        List<User> userList = factory.manufacturePojo(List.class, User.class);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.getAllUsers();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(users.size(), userList.size());
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(users.getFirst().getId(), userList.getFirst().getId());
        Assertions.assertEquals(users.getFirst().getName(), userList.getFirst().getName());
        Assertions.assertEquals(users.getFirst().getEmail(), userList.getFirst().getEmail());
    }

    @Test
    void testCreateUsersFailed() throws Exception {

        User user = factory.manufacturePojo(User.class);

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        Assertions.assertThrows(Exception.class, () -> userService.createUser(user.getName(), user.getEmail()));
    }

    @Test
    void testCreateUsersSuccess() throws Exception {

        User user = factory.manufacturePojo(User.class);

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User createdUser = userService.createUser(user.getName(), user.getEmail());

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals(createdUser.getId(), user.getId());
        Assertions.assertEquals(createdUser.getName(), user.getName());
        Assertions.assertEquals(createdUser.getEmail(), user.getEmail());

    }

    @Test
    void testChangeEmailButUserNotFound() {

        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.changeEmail(1L, request));
    }

    @Test
    void testChangeEmailButEmailAlreadyExist() {

        User user1 = factory.manufacturePojo(User.class);
        User user2 = factory.manufacturePojo(User.class);
        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user2));

        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.changeEmail(1L, request));
    }

    @Test
    void testChangeEmailSuccess() {

        User user1 = factory.manufacturePojo(User.class);
        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        userService.changeEmail(1L, request);
    }
}
