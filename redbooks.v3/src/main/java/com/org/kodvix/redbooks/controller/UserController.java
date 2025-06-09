package com.org.kodvix.redbooks.controller;

import com.org.kodvix.redbooks.dto.UserLoginDto;
import com.org.kodvix.redbooks.dto.UserProfileDto;
import com.org.kodvix.redbooks.dto.UserRegisterDto;
import com.org.kodvix.redbooks.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Operations related to user registration, login and profile management")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Register user (USER / SCHOOL_ADMIN / PUBLISHER_ADMIN)",
            description = "Registers a new user with one of the roles: USER, SCHOOL_ADMIN, or PUBLISHER_ADMIN")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDto dto) {
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @Operation(summary = "Login user (USER / SCHOOL_ADMIN / PUBLISHER_ADMIN)",
            description = "Authenticates a user with email and password. Use email as username for Basic Auth."
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDto dto) {
        return ResponseEntity.ok(userService.loginUser(dto));
    }

    @Operation(summary = "Get user profile",
            description = "Returns profile details for USER, SCHOOL_ADMIN, or PUBLISHER_ADMIN")
    @PreAuthorize("hasAnyRole('USER', 'SCHOOL_ADMIN', 'PUBLISHER_ADMIN')")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserProfile(email));
    }
//    @Operation(summary = "Get user profile")
//    @PostMapping("/profile")
//    public ResponseEntity<UserProfileDto> getProfile(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        return ResponseEntity.ok(userService.getUserProfile(email));
//    }


    @Operation(summary = "Update user profile",
            description = "Updates profile information for USER, SCHOOL_ADMIN, or PUBLISHER_ADMIN")
    @PreAuthorize("hasAnyRole('USER', 'SCHOOL_ADMIN', 'PUBLISHER_ADMIN')")
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(
            @RequestParam String email,
            @RequestBody @Valid UserProfileDto dto) {
        return ResponseEntity.ok(userService.updateUserProfile(email, dto));
    }

}
