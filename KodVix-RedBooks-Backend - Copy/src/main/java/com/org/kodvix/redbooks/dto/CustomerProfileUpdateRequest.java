package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerProfileUpdateRequest {
    @NotBlank
    private String schoolName;

    @NotBlank
    private String studentClass;

    private String address;
}
