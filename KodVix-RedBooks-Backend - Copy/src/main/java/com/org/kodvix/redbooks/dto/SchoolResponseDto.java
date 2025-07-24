package com.org.kodvix.redbooks.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SchoolResponseDto {
    private Long schoolId;
    private String name;
    private String email;

}
