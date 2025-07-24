package com.org.kodvix.redbooks.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassesResponseDto {
    private SchoolSummaryDto school;
    private List<ClassSummaryDto> classes;
}
