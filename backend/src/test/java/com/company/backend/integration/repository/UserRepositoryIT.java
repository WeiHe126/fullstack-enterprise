package com.company.backend.integration.repository;

import com.company.backend.model.entity.User;
import com.company.backend.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private PodamFactory factory;

    @Before
    public void setUp() {
        factory = new PodamFactoryImpl();
    }

    @Test
    public void testFindByEmail() {

        User user = factory.manufacturePojo(User.class);
        user.setId(null);

        entityManager.persist(user);
        entityManager.flush();

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        Assert.assertTrue(optionalUser.isPresent());
        Assert.assertEquals(optionalUser.get().getEmail(), user.getEmail());
    }

    @Test
    public void testFindByEmailNotFound() {

        User user = factory.manufacturePojo(User.class);

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        Assert.assertFalse(optionalUser.isPresent());
    }

}
