package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dto.BookDto;
import com.org.kodvix.redbooks.dto.PublisherDto;

import java.util.List;

public interface PublisherService {

    // View and update publisher profile
    PublisherDto updateProfile(String publisherName, PublisherDto profileDto);

    PublisherDto viewProfile(String publisherName);

    List<BookDto> getBooksByPublisher(String publisherName);

    // Optional enhancements for business logic (not directly tied to books)
    Object viewSalesReports(String publisherName);

    void respondToReview(Long reviewId, String response);

    Object viewNotifications(String publisherName);
}
