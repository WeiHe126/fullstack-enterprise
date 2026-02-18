package com.company.backend.integration.controller;

import com.company.backend.model.dto.ChangeEmailRequestDto;
import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserChangeEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private PodamFactory factory;

    @Before
    public void setUp() {
        userRepository.deleteAll();
        factory = new PodamFactoryImpl();
    }

    @Test
    public void testChangeEmailButUserNotFound() throws Exception {

        User user1 = factory.manufacturePojo(User.class);
        user1.setId(null);

        userRepository.save(user1);
        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        mockMvc.perform(post("/api/users/999999/change-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testChangeEmailButEmailAlreeadyExist() throws Exception {

        User user1 = factory.manufacturePojo(User.class);
        user1.setId(null);

        User savedUser = userRepository.save(user1);
        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);
        request.setEmail(user1.getEmail());

        mockMvc.perform(post("/api/users/" + savedUser.getId() + "/change-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testChangeEmailButEmailAlreeadySucess() throws Exception {

        User user1 = factory.manufacturePojo(User.class);
        user1.setId(null);

        User savedUser = userRepository.save(user1);
        ChangeEmailRequestDto request = factory.manufacturePojo(ChangeEmailRequestDto.class);

        mockMvc.perform(post("/api/users/" + savedUser.getId() + "/change-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

}
