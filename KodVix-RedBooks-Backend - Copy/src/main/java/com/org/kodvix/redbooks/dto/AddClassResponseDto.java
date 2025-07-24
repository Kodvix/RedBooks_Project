package com.org.kodvix.redbooks.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddClassResponseDto {
    private String message;
    private ClassSummaryDto classInfo;
    private SchoolSummaryDto schoolInfo;
}
