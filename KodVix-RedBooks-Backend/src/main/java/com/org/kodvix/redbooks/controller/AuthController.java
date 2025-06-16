package com.org.kodvix.redbooks.controller;

import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Register and authenticate users")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new customer and return a JWT")
    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest request) {
        return ResponseEntity.ok(authenticationService.registerCustomer(request));
    }

    @Operation(summary = "Register a new school and return a JWT")
    @PostMapping("/register/school")
    public ResponseEntity<AuthResponse> registerSchool(
            @Valid @RequestBody RegisterSchoolRequest request) {
        return ResponseEntity.ok(authenticationService.registerSchool(request));
    }

    @Operation(summary = "Register a new publisher and return a JWT")
    @PostMapping("/register/publisher")
    public ResponseEntity<AuthResponse> registerPublisher(
            @Valid @RequestBody RegisterPublisherRequest request) {
        return ResponseEntity.ok(authenticationService.registerPublisher(request));
    }

    @Operation(summary = "Authenticate existing user and return a JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
