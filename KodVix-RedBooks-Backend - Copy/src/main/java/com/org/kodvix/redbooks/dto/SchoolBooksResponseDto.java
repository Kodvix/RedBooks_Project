package com.org.kodvix.redbooks.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolBooksResponseDto {
    private SchoolSummaryDto school;
    private List<BookWithClassNamesDto> books;
}