package com.org.kodvix.redbooks.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassWithBookNamesDto {
    private Long classId;
    private String className;
    private List<String> bookAssigned;
}
