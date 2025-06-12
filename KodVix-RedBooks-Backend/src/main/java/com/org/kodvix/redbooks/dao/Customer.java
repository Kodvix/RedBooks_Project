package com.org.kodvix.redbooks.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
    @Column(nullable = true)
    private String schoolName;

    @Column(nullable = true)
    private String studentClass;
}
