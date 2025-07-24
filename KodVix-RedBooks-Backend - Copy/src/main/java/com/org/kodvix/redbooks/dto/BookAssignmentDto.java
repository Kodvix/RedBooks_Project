package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class BookAssignmentDto {
    private Long bookId;
    private String title;
}
