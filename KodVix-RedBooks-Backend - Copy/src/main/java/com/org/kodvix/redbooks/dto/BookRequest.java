package com.org.kodvix.redbooks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank
    private String title;

    private String author;
    private String subject;
}
