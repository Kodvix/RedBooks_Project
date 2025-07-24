package com.org.kodvix.redbooks.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolClassBookOverviewResponse {
    private Long schoolId;
    private String name;
    private List<ClassWithBookNamesDto> classes;
}
