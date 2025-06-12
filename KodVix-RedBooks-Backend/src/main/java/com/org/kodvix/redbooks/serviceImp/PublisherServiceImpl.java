package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.repository.*;
import com.org.kodvix.redbooks.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService{
    private final SchoolRepository schoolRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
