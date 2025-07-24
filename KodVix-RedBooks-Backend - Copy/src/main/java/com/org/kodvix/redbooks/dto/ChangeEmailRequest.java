package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeEmailRequest {

    @Email
    @NotBlank
    private String newEmail;
}
