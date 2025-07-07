package com.org.kodvix.redbooks.controller;

import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Register, authenticate, and manage user credentials")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new customer (no token returned)")
    @PostMapping("/register/customer")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        authenticationService.registerCustomer(request);
        return ResponseEntity.ok("Customer registered successfully. Please log in to get your token.");
    }

    @Operation(summary = "Register a new school (no token returned)")
    @PostMapping("/register/school")
    public ResponseEntity<String> registerSchool(@Valid @RequestBody RegisterSchoolRequest request) {
        authenticationService.registerSchool(request);
        return ResponseEntity.ok("School registered successfully. Please log in to get your token.");
    }

    @Operation(summary = "Register a new publisher (no token returned)")
    @PostMapping("/register/publisher")
    public ResponseEntity<String> registerPublisher(@Valid @RequestBody RegisterPublisherRequest request) {
        authenticationService.registerPublisher(request);
        return ResponseEntity.ok("Publisher registered successfully. Please log in to get your token.");
    }



    @Operation(summary = "Authenticate user and return JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }



    @Operation(summary = "Change logged-in user's email and get a new JWT")
    @PutMapping("/change-email")
    public ResponseEntity<AuthResponse> changeEmail(
            @Valid @RequestBody ChangeEmailRequest request,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(authenticationService.changeEmail(request, user.getUsername()));
    }

    @Operation(summary = "Update logged-in user's password")
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails user) {
        authenticationService.changePassword(request, user.getUsername());
        return ResponseEntity.ok("Password updated successfully.");
    }



    @Operation(summary = "Send OTP for password reset")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authenticationService.forgotPassword(request);
        return ResponseEntity.ok("OTP sent to your email.");
    }

    @Operation(summary = "Verify OTP and reset password")
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        authenticationService.verifyOtpAndResetPassword(request);
        return ResponseEntity.ok("Password reset successfully.");
    }

}
