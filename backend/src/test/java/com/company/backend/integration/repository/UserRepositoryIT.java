package com.company.backend.integration.repository;

import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

@DataJpaTest
class UserRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private PodamFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new PodamFactoryImpl();
    }

    @Test
    void testFindByEmail() {

        User user = factory.manufacturePojo(User.class);
        user.setId(null);

        entityManager.persist(user);
        entityManager.flush();

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals(optionalUser.get().getEmail(), user.getEmail());
    }

    @Test
    void testFindByEmailNotFound() {

        User user = factory.manufacturePojo(User.class);

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        Assertions.assertFalse(optionalUser.isPresent());
    }

}
