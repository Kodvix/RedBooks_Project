package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBySchoolId(Long schoolId);

}
