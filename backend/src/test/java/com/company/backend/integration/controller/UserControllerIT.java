package com.company.backend.integration.controller;

import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private PodamFactory factory;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        factory = new PodamFactoryImpl();
    }

    @Test
    void testListUsersEmpty() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testListUsers() throws Exception {

        User user1 = factory.manufacturePojo(User.class);
        User user2 = factory.manufacturePojo(User.class);

        user1.setId(null);
        user2.setId(null);

        userRepository.save(user1);
        userRepository.save(user2);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(user1.getName())))
                .andExpect(jsonPath("$[1].name", is(user2.getName())));
    }

    @Test
    void testCreateUserError() throws Exception {

        User user1 = factory.manufacturePojo(User.class);
        user1.setId(null);

        userRepository.save(user1);

        Assertions.assertThrows(ServletException.class, () ->
                mockMvc.perform(post("/api/users")
                                .param("username", user1.getName())
                                .param("email", user1.getEmail())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                        .andExpect(status().isInternalServerError())
        );
    }

    @Test
    void testCreateUserSucess() throws Exception {

        User user1 = factory.manufacturePojo(User.class);
        user1.setId(null);

        mockMvc.perform(post("/api/users")
                        .param("username", user1.getName())
                        .param("email", user1.getEmail())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(user1.getName())));
    }

}
