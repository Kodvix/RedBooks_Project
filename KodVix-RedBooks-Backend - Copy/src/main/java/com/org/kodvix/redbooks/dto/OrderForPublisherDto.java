package com.org.kodvix.redbooks.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OrderForPublisherDto {
    private Long orderId;
    private String customerName;
    private String schoolName;
    private String className;
    private String status;
    private List<BookBriefDto> books;
}
