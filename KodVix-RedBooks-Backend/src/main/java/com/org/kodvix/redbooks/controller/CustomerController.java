package com.org.kodvix.redbooks.controller;

import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.Customer;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.mapper.CustomerMapper;
import com.org.kodvix.redbooks.mapper.OrderMapper;
import com.org.kodvix.redbooks.service.CustomerService;
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
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "Customer actions: view books & place orders")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    private final OrderMapper orderMapper;

    @Operation(summary = "Get books assigned to the customer's class")
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooksForCustomer(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(customerService.getBooksForCustomer(user.getUsername()));
    }
    @Operation(summary = "Get books by class name and school name")
    @GetMapping("/books-by-class")
    public ResponseEntity<List<Book>> getBooksByClassNameAndSchool(
            @RequestParam String className,
            @RequestParam String schoolName) {
        return ResponseEntity.ok(customerService.getBooksByClassNameAndSchool(className, schoolName));
    }
//    @Operation(summary = "Place a new order for the customer")
//    @PostMapping("/orders")
//    public ResponseEntity<Order> placeOrder(
//            @AuthenticationPrincipal UserDetails user,
//            @Valid @RequestBody OrderRequest request) {
//        return ResponseEntity.ok(customerService.placeOrder(user.getUsername(), request.getClassId()));
//    }

    @Operation(summary = "Get all orders placed by the customer")
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getCustomerOrders(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(customerService.getOrders(user.getUsername()));
    }
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<CustomerProfileResponseDto>> updateProfile(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody CustomerProfileUpdateRequest request) {

        ApiResponse<CustomerProfileResponseDto> response =
                customerService.updateCustomerProfile(user.getUsername(), request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get books assigned to the customer's class (by customerId)")
    @GetMapping("/booksBy-customerId")
    public ResponseEntity<ApiResponse<List<BookBriefDto>>> getBooksForCustomer(
            @RequestParam Long customerId) {

        CustomerBooksResponse response = customerService.getBooksWithCustomerNameById(customerId);

        String msg = String.format(
                "Books retrieved successfully for %s (Class: %s, School: %s).",
                response.getCustomerName(),
                response.getClassName(),
                response.getSchoolName()
        );

        return ResponseEntity.ok(
                ApiResponse.<List<BookBriefDto>>builder()
                        .message(msg)
                        .data(response.getBooks())
                        .build()
        );
    }
    @Operation(summary = "Get all orders placed by customerId (public)")
    @GetMapping("/orders-by-customerId")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getCustomerOrdersById(
            @RequestParam Long customerId) {
        return ResponseEntity.ok(customerService.getOrdersByCustomerId(customerId));
    }
    @GetMapping("/show-all-customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(
                customerService.getAllCustomers().stream()
                        .map(customerMapper::toResponse)
                        .toList()
        );
    }
//    @DeleteMapping("/delete-customer/{customerId}")
//    @Operation(summary = "Delete a customer by ID (admin only)")
//    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
//        customerService.deleteCustomer(customerId);
//        return ResponseEntity.ok("Customer deleted successfully.");
//    }
    @DeleteMapping("/orders/{orderId}")
    @Operation(summary = "Cancel an order (customer only)")
    public ResponseEntity<String> cancelOrder(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long orderId) {
        customerService.cancelOrder(orderId, user.getUsername());
        return ResponseEntity.ok("Order cancelled successfully.");
    }
    @Operation(summary = "Delete my own customer account")
    @DeleteMapping("/delete-account")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> deleteMyAccount(@AuthenticationPrincipal UserDetails user) {
        customerService.deleteOwnAccount(user.getUsername());
        return ResponseEntity.ok("Your account has been deleted successfully.");
    }

    @Operation(summary = "Place a new order using className and schoolName")
    @PostMapping("/orders/by-school-and-class")
    public ResponseEntity<ApiResponse<OrderSuccessResponse>> placeOrderByName(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody OrderByNameRequest request) {

        OrderSuccessResponse response = customerService.placeOrderByClassName(user.getUsername(), request.className(), request.schoolName());

        return ResponseEntity.ok(
                ApiResponse.<OrderSuccessResponse>builder()
                        .message("Order placed successfully.")
                        .data(response)
                        .build()
        );
    }

}
