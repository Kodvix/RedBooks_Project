package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SchoolProfileUpdateRequest {

    @NotBlank
    private String name;

    // Add more school-specific fields if needed in the future
}
