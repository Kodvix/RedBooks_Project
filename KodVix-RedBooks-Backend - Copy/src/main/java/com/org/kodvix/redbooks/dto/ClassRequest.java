package com.org.kodvix.redbooks.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassRequest {
    @NotBlank
    private String className;
}
