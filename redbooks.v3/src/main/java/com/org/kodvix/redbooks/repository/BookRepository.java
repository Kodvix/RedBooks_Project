package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.PublisherDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.kodvix.redbooks.dao.BookDao;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookDao, Long> {
    // You can add custom queries here if needed, e.g. findByIsbn, findByAuthor, etc.
    boolean existsByIsbn(String isbn);
    List<BookDao> findByPublisherRef_PublisherName(String publisherName);

    List<BookDao> findByPublisherRef(PublisherDao publisherRef);
    
    List<BookDao> findByClassName(String className);



}
