// BookUpdateResponseDto.java
package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookUpdateResponseDto {
    private Long bookId;
    private String title;
    private String author;
    private String subject;
    private Long schoolId;
    private String schoolName;
}
