package com.org.kodvix.redbooks.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CustomerBooksResponse {
    private String customerName;
    private String className;
    private String schoolName;
    private List<BookBriefDto> books;
}
