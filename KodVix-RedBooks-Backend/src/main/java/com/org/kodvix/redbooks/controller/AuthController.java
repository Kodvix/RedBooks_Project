package com.org.kodvix.redbooks.controller;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        return ResponseEntity.ok(authenticationService.registerCustomer(request));
    }

    @PostMapping("/register/school")
    public ResponseEntity<AuthResponse> registerSchool(@Valid @RequestBody RegisterSchoolRequest request) {
        return ResponseEntity.ok(authenticationService.registerSchool(request));
    }

    @PostMapping("/register/publisher")
    public ResponseEntity<AuthResponse> registerPublisher(@Valid @RequestBody RegisterPublisherRequest request) {
        return ResponseEntity.ok(authenticationService.registerPublisher(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
