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
@Tag(name = "Authentication API", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new customer")
    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        return ResponseEntity.ok(authenticationService.registerCustomer(request));
    }

    @Operation(summary = "Register a new school")
    @PostMapping("/register/school")
    public ResponseEntity<AuthResponse> registerSchool(@Valid @RequestBody RegisterSchoolRequest request) {
        return ResponseEntity.ok(authenticationService.registerSchool(request));
    }

    @Operation(summary = "Register a new publisher")
    @PostMapping("/register/publisher")
    public ResponseEntity<AuthResponse> registerPublisher(@Valid @RequestBody RegisterPublisherRequest request) {
        return ResponseEntity.ok(authenticationService.registerPublisher(request));
    }

    @Operation(summary = "Authenticate and login a user")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
