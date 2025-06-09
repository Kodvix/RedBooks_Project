package com.org.kodvix.redbooks.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "school")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolDao
{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

//    @Column(name = "email_id", unique = true, nullable = false)
//    private String schoolEmail;//

//    @Column(name = "password")
//    private String schoolPassword;


    @Column(nullable = false)
    private String schoolType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "language_medium")
    private String medium;

    @Column(nullable = false)
    private String board;

//	private String logoUrl;

    @Column(name = "socialMediaUrl")
    private String socialMediaUrl;


    @Column(name = "created_at")
    private Date createdAt;


    @Column(name = "updated_at")
    private Date updatedAt;

    private String description;

    @OneToOne(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private ContactDao contact;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchoolClassDao> classes;

    @ManyToMany
    @JoinTable(
            name = "school_recommended_publishers",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private List<PublisherDao> recommendedPublishers = new ArrayList<>();



}
