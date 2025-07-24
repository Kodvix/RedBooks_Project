package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PublisherProfileUpdateRequest {

    @NotBlank
    private String name;

    // Add more publisher-specific profile fields if needed in the future
}
