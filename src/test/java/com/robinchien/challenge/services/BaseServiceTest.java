package com.robinchien.challenge.services;

import com.robinchien.challenge.SynpulseApplication;
import com.robinchien.challenge.testcontainers.config.ContainersEnvironment;
import com.robinchien.challenge.models.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SynpulseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseServiceTest extends ContainersEnvironment {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected AuthenticationService authenticationService;
    @Autowired
    protected JwTokenService jwTokenService;

}
