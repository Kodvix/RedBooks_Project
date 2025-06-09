package com.org.kodvix.redbooks.controller;

import com.org.kodvix.redbooks.dto.BookDto;
import com.org.kodvix.redbooks.exception.ResourceNotFoundException;
import com.org.kodvix.redbooks.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Book API", description = "Operations for managing books")
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Add a new book")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<BookDto> addBook(
            @RequestPart("book") BookDto bookDto,
            @RequestPart(value = "orderDocument", required = false) MultipartFile orderDocument) {
        try {
            BookDto savedBook = bookService.addBook(bookDto, orderDocument);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            // You might want to log or handle other exceptions like DataIntegrityViolationException
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Get a book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        try {
            BookDto bookDto = bookService.getBookById(id);
            return ResponseEntity.ok(bookDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @Operation(summary = "Get a book by ClassName")
    @GetMapping("/className/{className}")
    public ResponseEntity<List<BookDto>> getBookByClassName(@PathVariable String className) {
        try {
            List<BookDto> bookDto = bookService.getBookByClassName(className);
            return ResponseEntity.ok(bookDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Update a book")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @RequestPart("book") BookDto bookDto,
            @RequestPart(value = "orderDocument", required = false) MultipartFile orderDocument) {
        try {
            BookDto updatedBook = bookService.updateBook(id, bookDto, orderDocument);
            return ResponseEntity.ok(updatedBook);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete a book")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted successfully with ID: " + id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with ID: " + id);
        }
    }

}
