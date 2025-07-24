// PublisherProfileResponseDto.java
package com.org.kodvix.redbooks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublisherProfileResponseDto {
    private Long id;
    private String name;
    private String email;
}
