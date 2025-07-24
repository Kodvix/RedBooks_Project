package com.org.kodvix.redbooks.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long bookId;
    private String title;
    private String author;
    private String subject;
    private SchoolSummaryDto school;
    private List<String> assignedToClasses;
    private boolean isAssigned;
}
