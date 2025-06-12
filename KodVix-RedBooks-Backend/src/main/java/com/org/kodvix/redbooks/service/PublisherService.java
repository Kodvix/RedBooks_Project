package com.org.kodvix.redbooks.service;
import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.dao.School;

import java.util.List;

public interface PublisherService {
    List<School> getAllSchools();
    List<Book> getAllBooks();
    List<Order> getAllOrders();
}
