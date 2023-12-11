package com.robinchien.challenge.services;

import com.robinchien.challenge.models.auth.AuthenticationResponse;
import com.robinchien.challenge.models.auth.SignInRequest;
import com.robinchien.challenge.models.auth.SignUpRequest;
import com.robinchien.challenge.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthenticationServiceTest extends BaseServiceTest {

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void register() {
        String username = "test";
        AuthenticationResponse resp = authenticationService.signup(new SignUpRequest(username, "test"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var token = resp.getToken();
        assertEquals(user.getUsername(), jwTokenService.extractUsername(token));
    }

    @Test
    void authenticate() {
        String username = "test";
        authenticationService.signup(new SignUpRequest(username, "test"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        AuthenticationResponse respAuth = authenticationService.signin(new SignInRequest(username, "test"));
        var token = respAuth.getToken();
        assertEquals(user.getUsername(), jwTokenService.extractUsername(token));
    }
}