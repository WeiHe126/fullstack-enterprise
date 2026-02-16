package com.company.backend.service;

import com.company.backend.exception.EmailAlreadyExistsException;
import com.company.backend.exception.UserNotFoundException;
import com.company.backend.model.dto.ChangeEmailRequestDto;
import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import com.company.backend.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private PodamFactory factory;

    @Before
    public void setUp() {
        factory = new PodamFactoryImpl();
    }

    @Test
    public void testGetAllUsers(){

        List<User> userList = factory.manufacturePojo(List.class, User.class);

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.getAllUsers();

        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), userList.size());
        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals(users.getFirst().getId(), userList.getFirst().getId());
        Assert.assertEquals(users.getFirst().getName(), userList.getFirst().getName());
        Assert.assertEquals(users.getFirst().getEmail(), userList.getFirst().getEmail());
    }

    @Test(expected = Exception.class)
    public void testCreateUsersFailed() throws Exception {

        User user = factory.manufacturePojo(User.class);

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));

        userService.createUser(user.getName(), user.getEmail());
    }

    @Test
    public void testCreateUsersSuccess() throws Exception {

        User user = factory.manufacturePojo(User.class);

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User createdUser = userService.createUser(user.getName(), user.getEmail());

        Assert.assertNotNull(createdUser);
        Assert.assertEquals(createdUser.getId(), user.getId());
        Assert.assertEquals(createdUser.getName(), user.getName());
        Assert.assertEquals(createdUser.getEmail(), user.getEmail());

    }

    @Test(expected = UserNotFoundException.class)
    public void testChangeEmailButUserNotFound() {

        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        userService.changeEmail(1L, request);
    }

    @Test(expected = EmailAlreadyExistsException.class)
    public void testChangeEmailButEmailAlreadyExist() {

        User user1 = factory.manufacturePojo(User.class);
        User user2 = factory.manufacturePojo(User.class);
        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user2));

        userService.changeEmail(1L, request);
    }

    @Test
    public void testChangeEmailSuccess() {

        User user1 = factory.manufacturePojo(User.class);
        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        userService.changeEmail(1L, request);
    }
}
