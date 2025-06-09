package com.org.kodvix.redbooks.controller;

import com.org.kodvix.redbooks.dto.BookDto;
import com.org.kodvix.redbooks.dto.PublisherDto;
import com.org.kodvix.redbooks.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Publisher API", description = "Endpoints for publishers to manage their profiles")
@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
@Validated
public class PublisherController {

    private final PublisherService publisherService;

    @Operation(summary = "View publisher profile by name")
    @GetMapping("/profile")
    public ResponseEntity<PublisherDto> viewProfile(@RequestParam("publisherName") String publisherName) {
        PublisherDto profile = publisherService.viewProfile(publisherName);
        return ResponseEntity.ok(profile);
    }

    @Operation(summary = "Update publisher profile")
    @PutMapping("/profile")
    public ResponseEntity<PublisherDto> updateProfile(
            @RequestParam("publisherName") String publisherName,
            @Valid @RequestBody PublisherDto profileDto) {
        PublisherDto updatedProfile = publisherService.updateProfile(publisherName, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @Operation(summary = "Get all books by publisher name")
    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBooksByPublisher(
            @RequestParam("publisherName") String publisherName) {
        List<BookDto> books = publisherService.getBooksByPublisher(publisherName);
        return ResponseEntity.ok(books);
    }

    // Optional endpoints can remain but throw UnsupportedOperationException in service:
    // viewSalesReports, respondToReview, viewNotifications
}
