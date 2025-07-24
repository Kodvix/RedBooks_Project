package com.org.kodvix.redbooks.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String status;
    private Long customerId;
    private String customerName;
    private Long classId;
    private String className;
    private Long schoolId;
    private String schoolName;
    private List<BookSummary> books;
    @Data
    @AllArgsConstructor
    public static class BookSummary {
        private Long bookId;
        private String title;
        private String author;
        private String subject;
    }
}