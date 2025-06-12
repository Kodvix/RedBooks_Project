package com.org.kodvix.redbooks.controller;
import com.org.kodvix.redbooks.dto.OrderRequest;
import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(customerService.getBooksForCustomer(user.getUsername()));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@AuthenticationPrincipal UserDetails user,
                                            @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(customerService.placeOrder(user.getUsername(), request.getClassId()));
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(customerService.getOrders(user.getUsername()));
    }
}
