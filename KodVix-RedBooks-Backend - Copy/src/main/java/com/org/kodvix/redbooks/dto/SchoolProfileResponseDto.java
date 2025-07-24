// SchoolProfileResponseDto.java
package com.org.kodvix.redbooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolProfileResponseDto {
    private Long schoolId;
    private String name;
    private String email;
}
