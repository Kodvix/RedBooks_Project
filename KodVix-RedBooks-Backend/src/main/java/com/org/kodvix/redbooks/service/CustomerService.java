package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.Customer;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.dto.*;

import java.util.List;

public interface CustomerService {
    List<Book> getBooksForCustomer(String customerEmail);

    //List<BookBriefDto> getBooksForCustomerById(Long customerId);//replaced just now

    CustomerBooksResponse getBooksWithCustomerNameById(Long customerId);

    Order placeOrder(String customerEmail, Long classId);
    // OrderRequest placeOrder(String customerEmail, Long classId);
    List<Order> getOrders(String customerEmail);

    //List<Order> getOrdersByCustomerId(Long customerId);
    ApiResponse<List<OrderResponse>> getOrdersByCustomerId(Long customerId);

    //Customer updateCustomerProfile(String email, CustomerProfileUpdateRequest request);

    ApiResponse<CustomerProfileResponseDto> updateCustomerProfile(String email, CustomerProfileUpdateRequest request);
    List<Customer> getAllCustomers();

    void deleteCustomer(Long customerId);

    void deleteOwnAccount(String email);

    void cancelOrder(Long orderId, String customerEmail);

    List<Book> getBooksByClassNameAndSchool(String className, String schoolName);

    OrderSuccessResponse placeOrderByClassName(String customerEmail, String className, String schoolName);

}
