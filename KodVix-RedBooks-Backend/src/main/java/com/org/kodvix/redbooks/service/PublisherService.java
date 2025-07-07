package com.org.kodvix.redbooks.service;
import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.dao.Publisher;
import com.org.kodvix.redbooks.dao.School;
import com.org.kodvix.redbooks.dto.*;

import java.util.List;

public interface PublisherService {
    //List<School> getAllSchools();
    List<SchoolForPublisherDto> getAllSchoolsForPublisher();
    //List<Book> getAllBooks();

    List<BookForPublisherDto> getAllBooksForPublisher();

    //List<Order> getAllOrders();

    List<OrderForPublisherDto> getAllOrdersForPublisher();

    //Publisher updatePublisherProfile(String email, PublisherProfileUpdateRequest request);

    ApiResponse<PublisherProfileResponseDto> updatePublisherProfile(String email, PublisherProfileUpdateRequest request);

    List<Publisher> getAllPublishers();

    void deletePublisher(Long publisherId);

    public void deleteOwnPublisher(String email);

}
