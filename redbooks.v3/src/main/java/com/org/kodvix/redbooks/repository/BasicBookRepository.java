package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.BookDao;
import com.org.kodvix.redbooks.dao.SchoolClassDao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.org.kodvix.redbooks.dao.ClassBookDao;

public interface BasicBookRepository extends JpaRepository<ClassBookDao,Long> {
    boolean existsBySchoolClassAndMasterBook(SchoolClassDao schoolClass, BookDao masterBook);
}
