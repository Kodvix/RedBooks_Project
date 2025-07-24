package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dto.BookRequest;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toDao(BookRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .subject(request.getSubject())
                .build();
    }
    public BookRequest toDto(Book book) {
        BookRequest dto = new BookRequest();

        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        return dto;
    }


}