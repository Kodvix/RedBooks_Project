package com.org.kodvix.redbooks.controller;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.org.kodvix.redbooks.dto.BasicBookDto;
import com.org.kodvix.redbooks.service.BasicBookService;

@Tag(name = "Class Book API", description = "Manage books assigned to classes")
@RestController
@RequestMapping("/api/class/book")
public class BasicClassBookController {

    @Autowired
    private BasicBookService bookService;

    @Operation(summary = "Add a class book")
    @PostMapping("/addBook")
    public ResponseEntity<BasicBookDto> addBook(
            @RequestPart("book") @Valid BasicBookDto book,
            @RequestPart("image") MultipartFile image) {

        try {
            if (image != null && !image.isEmpty()) {
                book.setImage(image.getBytes());
            }
            BasicBookDto savedBook = bookService.addBook(book);
            return ResponseEntity.ok(savedBook);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // You might want to log the error here
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Get a class book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BasicBookDto> getBookById(@PathVariable("id") Long id) {
        try {
            BasicBookDto book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update a class book")
    @PutMapping("/update/{bookId}")
    public ResponseEntity<BasicBookDto> updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestPart("book") @Valid BasicBookDto book,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        try {
            if (image != null && !image.isEmpty()) {
                book.setImage(image.getBytes());
            }
            BasicBookDto updatedBook = bookService.updateBook(bookId, book);
            return ResponseEntity.ok(updatedBook);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Delete a class book")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long id) {
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete book");
        }
    }
}
