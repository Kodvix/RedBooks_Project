package com.org.kodvix.redbooks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private Long customerId;
    private String name;
    private String email;
    private String schoolName;
    private String studentClass;
    private String address;
}
