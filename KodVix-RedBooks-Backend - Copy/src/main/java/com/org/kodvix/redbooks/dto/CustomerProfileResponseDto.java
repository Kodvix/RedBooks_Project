// CustomerProfileResponseDto.java
package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerProfileResponseDto {
    private Long customerId;
    private String name;
    private String email;
    private String schoolName;
    private String studentClass;
    private String address;
}
