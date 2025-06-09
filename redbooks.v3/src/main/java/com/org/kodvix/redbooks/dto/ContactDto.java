package com.org.kodvix.redbooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long contactId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String email;
    private Long phoneNo;
    private String website_url;


}
