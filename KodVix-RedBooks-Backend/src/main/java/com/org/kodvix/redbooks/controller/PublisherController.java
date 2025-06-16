package com.org.kodvix.redbooks.controller;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor
@Tag(name = "Publisher API", description = "Endpoints for publishers to view books, schools, and orders")
public class PublisherController {
    private final PublisherService publisherService;

    @Operation(summary = "Get all registered schools")
    @GetMapping("/schools")
    public ResponseEntity<List<School>> getSchools() {
        return ResponseEntity.ok(publisherService.getAllSchools());
    }

    @Operation(summary = "Get all books published")
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(publisherService.getAllBooks());
    }

    @Operation(summary = "Get all orders placed")
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(publisherService.getAllOrders());
    }
}
