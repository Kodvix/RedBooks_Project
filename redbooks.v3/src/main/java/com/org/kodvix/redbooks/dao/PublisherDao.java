package com.org.kodvix.redbooks.dao;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String publisherName;

    private String email;

    private String phoneNumber;

    private String address;
    
    @OneToMany(mappedBy = "publisher")
    private List<OrderDao> handledOrders;

    // One publisher can have many books
    @OneToMany(mappedBy = "publisherRef", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookDao> books = new ArrayList<>();

    @ManyToMany(mappedBy = "recommendedPublishers")
    private List<SchoolDao> recommendedBySchools = new ArrayList<>();
}

