package com.org.kodvix.redbooks.controller;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor

public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping("/schools")
    public ResponseEntity<List<School>> getSchools() {
        return ResponseEntity.ok(publisherService.getAllSchools());
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(publisherService.getAllBooks());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(publisherService.getAllOrders());
    }
}
