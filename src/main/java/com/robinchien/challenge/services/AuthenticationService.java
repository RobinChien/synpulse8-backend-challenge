package com.robinchien.challenge.services;

import com.robinchien.challenge.config.UserAlreadyExistAuthenticationException;
import com.robinchien.challenge.models.auth.AuthenticationResponse;
import com.robinchien.challenge.models.auth.SignInRequest;
import com.robinchien.challenge.models.auth.SignUpRequest;
import com.robinchien.challenge.models.user.User;
import com.robinchien.challenge.models.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwTokenService jwTokenService;

    public AuthenticationResponse signup(SignUpRequest request) {
        // Check if user already exists

        if(userRepository.findByUsername(request.getUsername()).isPresent()){

            //Throw custom exception
            throw new UserAlreadyExistAuthenticationException("User with username: " + request.getUsername() + " already exists");
        }

        // Create new user
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // Save user
        userRepository.save(user);

        // Generate token
        var jwtToken = jwTokenService.generateToken(user);

        // Return token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse signin(SignInRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Get user
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        // Generate token
        var jwtToken = jwTokenService.generateToken(user);

        // Return token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
