package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dao.BookDao;
import com.org.kodvix.redbooks.dto.BookDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    BookDto addBook(BookDto bookDto, MultipartFile orderDocument) throws IOException;

    BookDto updateBook(Long bookId, BookDto bookDto, MultipartFile orderDocument) throws IOException;

    void deleteBook(Long bookId);

    List<BookDto> getAllBooks();

    BookDto getBookById(Long bookId);

    List<BookDto> getBooksByPublisher(String publisherName);
    
    List<BookDto> getBookByClassName(String className);


}
