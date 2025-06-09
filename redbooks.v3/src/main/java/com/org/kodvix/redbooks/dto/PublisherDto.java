package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {
    private Long id;

    @NotBlank(message = "Publisher name is required")
    private String publisherName;

    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String address;
}
