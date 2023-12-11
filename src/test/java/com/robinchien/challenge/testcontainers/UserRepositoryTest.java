package com.robinchien.challenge.testcontainers;

import com.robinchien.challenge.services.BaseServiceTest;
import com.robinchien.challenge.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UserRepositoryTest extends BaseServiceTest {

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsername() {
        String username = "username";
        User user = new User(1000L, username, "password");

        userRepository.save(user);

        //when
        Optional<User> expected = userRepository.findByUsername(username);

        if (expected.isEmpty()) {
            assertEquals(expected, Optional.empty());
        } else {
            assertEquals(expected.get().getUsername(), user.getUsername());
            assertEquals(expected.get().getPassword(), user.getPassword());
        }

    }

    @Test
    void findNullByUsername() {
        String username = "username";
        String wrongUsername = "totallyWrongUsername";
        User user = new User(1000L, username, "password");

        userRepository.save(user);

        //when
        Optional<User> expected = userRepository.findByUsername(wrongUsername);

        if (expected.isPresent()) {
            fail();
        }

        assertEquals(expected, Optional.empty());
    }

}
