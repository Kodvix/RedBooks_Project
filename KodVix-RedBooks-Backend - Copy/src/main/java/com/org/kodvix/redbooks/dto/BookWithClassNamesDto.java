package com.org.kodvix.redbooks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookWithClassNamesDto {
    private Long bookId;
    private String title;
    private String author;
    private List<String> assignedToClasses;
    private boolean isAssigned;
}
//Minimal book info + assigned class names.
