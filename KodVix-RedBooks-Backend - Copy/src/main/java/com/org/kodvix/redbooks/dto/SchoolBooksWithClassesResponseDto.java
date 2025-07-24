package com.org.kodvix.redbooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolBooksWithClassesResponseDto {
    private SchoolSummaryDto school;
    private List<BookWithClassNamesDto> books;
}
//includes school details and a list of books with their assigned class names