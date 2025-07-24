// ClassUpdateResponseDto.java
package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassUpdateResponseDto {
    private Long classId;
    private String className;
    private Long schoolId;
    private String schoolName;
}
