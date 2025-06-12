package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dto.BookRequest;
import com.org.kodvix.redbooks.dto.ClassRequest;
import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.ClassEntity;

import java.util.List;
public interface SchoolService {
    ClassEntity addClass(ClassRequest request, String schoolEmail);
    Book addBook(BookRequest request, String schoolEmail);
    ClassEntity assignBooksToClass(Long classId, List<Long> bookIds, String schoolEmail);
    List<ClassEntity> getClassesBySchool(String schoolEmail);
    List<Book> getBooksBySchool(String schoolEmail);
}
