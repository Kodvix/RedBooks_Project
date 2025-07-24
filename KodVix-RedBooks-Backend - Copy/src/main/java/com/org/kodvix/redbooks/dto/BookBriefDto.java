package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookBriefDto {
    private String title;
    private String author;
    private String subject;
}
