package com.org.kodvix.redbooks.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "book")
public class ClassBookDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column
    private Integer edition;

    @Column
    private Integer publishedYear;
    //    YEAR
//
    @Column
    private String subject;
    //    SUBJECT
//
    @Column
    private String isbn;
//    ISBN
//


    //    CATEGORY
    @Column
    private String description;

    @Column
    private String language;

    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @JsonBackReference
    private SchoolClassDao schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_book_id", nullable = false)
    private BookDao masterBook;

}
