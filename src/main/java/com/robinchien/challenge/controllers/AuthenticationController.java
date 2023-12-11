package com.robinchien.challenge.controllers;

import com.robinchien.challenge.models.auth.SignInRequest;
import com.robinchien.challenge.models.auth.SignUpRequest;
import com.robinchien.challenge.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/signup")
    @Operation(
            description = "Register a user and retrieve generated JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully registered"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error message: e.getMessage()"
                    )
            }
    )
    public ResponseEntity<?> sighup(
            @RequestBody SignUpRequest request
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.signup(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    @Operation(
            description = "Authenticate user and retrieve generated JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully signed in"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error message: e.getMessage()"
                    )
            }
    )
    public ResponseEntity<?> signin(
            @RequestBody SignInRequest request
    ) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.signin(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
