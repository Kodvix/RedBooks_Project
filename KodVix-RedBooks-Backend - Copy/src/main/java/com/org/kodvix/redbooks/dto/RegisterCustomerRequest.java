package com.org.kodvix.redbooks.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class RegisterCustomerRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String schoolName;

    @NotBlank
    private String studentClass;

    private String address;
}
