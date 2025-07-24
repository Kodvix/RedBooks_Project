package com.org.kodvix.redbooks.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSummaryDto {
    private Long bookId;
    private String title;
    private String author;
}

//Minimal school info (used inside the response).
