package com.org.kodvix.redbooks.dto;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassBookAssignmentResponse {
    private Long schoolId;
    private Long classId;
    private String className;
    private List<BookSummary> assignedBooks;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BookSummary {
        private Long bookId;
        private String title;
        private String author;
    }
}
