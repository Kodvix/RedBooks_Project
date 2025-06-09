package com.org.kodvix.redbooks.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "book_price", nullable = false)
    private Double bookPrice;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String language;

    @Column(name = "number_of_pages", nullable = false)
    private Integer numberOfPages;

    @Column(nullable = false)
    private String edition;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "order_document", columnDefinition = "LONGBLOB")
    private byte[] orderDocument;

    @Column(name = "order_document_name")
    private String orderDocumentName;

    @Column(name = "order_document_type")
    private String orderDocumentType;
       
    @Column(name = "class_name")
    private String className;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private PublisherDao publisherRef;
}