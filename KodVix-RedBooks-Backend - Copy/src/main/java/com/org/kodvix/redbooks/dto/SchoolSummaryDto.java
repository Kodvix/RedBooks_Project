package com.org.kodvix.redbooks.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolSummaryDto {
    private Long schoolId;
    private String name;

}
