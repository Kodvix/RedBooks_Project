package com.org.kodvix.redbooks.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublisherResponse {
    private Long publisherId;
    private String name;
    private String email;
}
