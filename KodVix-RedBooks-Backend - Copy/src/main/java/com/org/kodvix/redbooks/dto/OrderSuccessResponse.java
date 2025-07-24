package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderSuccessResponse {
    private Long orderId;
    private String school;
    private String className;
    private String customerName;
    private String customerEmail;
    private String deliveryAddress;
    private List<BookBriefDto> orderedBooks;
    //private String message;
}
