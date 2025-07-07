package com.org.kodvix.redbooks.controller;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.mapper.OrderMapper;
import com.org.kodvix.redbooks.mapper.PublisherMapper;
import com.org.kodvix.redbooks.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor
@Tag(name = "Publisher API", description = "Endpoints for publishers to view books, schools, and orders")
public class PublisherController {
    private final PublisherService publisherService;
    private final OrderMapper orderMapper;
    private final PublisherMapper publisherMapper;

//    @Operation(summary = "Get all registered schools")
//    @GetMapping("/schools")
//    public ResponseEntity<List<School>> getSchools() {
//        return ResponseEntity.ok(publisherService.getAllSchools());
//    }
    @Operation(summary = "Get all registered schools (for publishers)")
    @GetMapping("/schools")
    public ResponseEntity<List<SchoolForPublisherDto>> getSchools() {
        return ResponseEntity.ok(publisherService.getAllSchoolsForPublisher());
    }


    @Operation(summary = "Get all books ")
    @GetMapping("/books")
    public ResponseEntity<List<BookForPublisherDto>> getBooks() {
        return ResponseEntity.ok(publisherService.getAllBooksForPublisher());
    }


//    @Operation(summary = "Get all books published")
//    @GetMapping("/books")
//    public ResponseEntity<List<Book>> getBooks() {
//        return ResponseEntity.ok(publisherService.getAllBooks());
//    }

//    @Operation(summary = "Get all orders placed")
//    @GetMapping("/orders/raw")
//    public ResponseEntity<List<Order>> getRawOrders() {
//        return ResponseEntity.ok(publisherService.getAllOrders());
//    }

    @Operation(summary = "Get all orders placed (publisher-friendly)")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderForPublisherDto>> getOrders() {
        return ResponseEntity.ok(publisherService.getAllOrdersForPublisher());
    }

    @Operation(summary = "Update publisher profile (name)")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<PublisherProfileResponseDto>> updateProfile(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody PublisherProfileUpdateRequest request) {

        ApiResponse<PublisherProfileResponseDto> response =
                publisherService.updatePublisherProfile(user.getUsername(), request);

        return ResponseEntity.ok(response);
    }


    //    @Operation(summary = "Get all books by publisher ID")
//    @GetMapping("/books/by-publisher")
//    public ResponseEntity<List<Book>> getBooks(@RequestParam Long publisherId) {
//        return ResponseEntity.ok(publisherService.getBooksByPublisherId(publisherId));
//    }
//    @GetMapping("/orders")
//    public ResponseEntity<List<OrderResponse>> getOrders() {
//        return ResponseEntity.ok(
//                publisherService.getAllOrders()
//                        .stream()
//                        .map(orderMapper::toResponse)
//                        .toList()
//        );
//    }
    @Operation(summary = "Get all registered publishers")
    @GetMapping("/show-all-publishers")
    public ResponseEntity<List<PublisherResponse>> getAllPublishers() {
        return ResponseEntity.ok(
                publisherService.getAllPublishers().stream()
                        .map(publisherMapper::toResponse)
                        .toList()
        );
    }
//    @DeleteMapping("/publisher/{publisherId}")
//    @Operation(summary = "Delete a publisher by ID (admin only)")
//    public ResponseEntity<String> deletePublisher(@PathVariable Long publisherId) {
//        publisherService.deletePublisher(publisherId);
//        return ResponseEntity.ok("Publisher deleted successfully.");
//    }
    @PreAuthorize("hasRole('PUBLISHER')")
    @DeleteMapping("/delete-profile")
    public ResponseEntity<String> deleteMyProfile(@AuthenticationPrincipal UserDetails user) {
        publisherService.deleteOwnPublisher(user.getUsername());
        return ResponseEntity.ok("Your publisher account has been deleted.");
    }

}


