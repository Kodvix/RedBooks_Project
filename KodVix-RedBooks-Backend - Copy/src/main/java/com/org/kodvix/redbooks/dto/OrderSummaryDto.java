package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data

public class OrderSummaryDto {
    private Long orderId;
    private String status;
    private String school;
    private String className;
    private List<BookBriefDto> books;
}
