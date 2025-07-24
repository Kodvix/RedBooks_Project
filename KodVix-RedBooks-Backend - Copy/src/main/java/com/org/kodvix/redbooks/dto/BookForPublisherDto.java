package com.org.kodvix.redbooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookForPublisherDto {
    private Long bookId;
    private String title;
    private String author;
    private String subject;
    private String schoolName;
}
