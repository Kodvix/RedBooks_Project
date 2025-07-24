package com.org.kodvix.redbooks.dto;
import jakarta.validation.constraints.NotBlank;
public record OrderByNameRequest(
        @NotBlank String className,
        @NotBlank String schoolName
) {}