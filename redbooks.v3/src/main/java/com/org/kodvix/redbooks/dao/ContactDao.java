package com.org.kodvix.redbooks.dao;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
public class ContactDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String email;
    private Long phoneNo;
    private String website_url;

    @OneToOne
    @JoinColumn(name = "school_Id", unique = true)
    @JsonBackReference
    private SchoolDao school;


}
