package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.dto.OrderRequest;

import java.util.List;

public interface CustomerService {
    List<Book> getBooksForCustomer(String customerEmail);
    Order placeOrder(String customerEmail, Long classId);
    // OrderRequest placeOrder(String customerEmail, Long classId);
    List<Order> getOrders(String customerEmail);
}
