package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.repository.*;
import com.org.kodvix.redbooks.service.PublisherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService{
    private final SchoolRepository schoolRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final PublisherRepository publisherRepository;

    private final UserRepository userRepository;

//    @Override
//    public List<School> getAllSchools() {
//        return schoolRepository.findAll();
//    }

    @Override
    public List<SchoolForPublisherDto> getAllSchoolsForPublisher() {
        return schoolRepository.findAll().stream()
                .map(school -> new SchoolForPublisherDto(
                        school.getId(),
                        school.getName(),
                        school.getEmail()
                ))
                .toList();
    }


    @Override
    public List<BookForPublisherDto> getAllBooksForPublisher() {
        return bookRepository.findAll().stream()
                .map(book -> new BookForPublisherDto(
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getSubject(),
                        book.getSchool() != null ? book.getSchool().getName() : "N/A"
                ))
                .toList();
    }

//    @Override
//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }

//    @Override
//    public List<Order> getAllOrders() {
//        return orderRepository.findAllWithDetails();
//    }
//@Override
//public List<Order> getAllOrders() {
//    return orderRepository.findAllOrdersWithDetails();
//}

    @Override
    public List<OrderForPublisherDto> getAllOrdersForPublisher() {
        return orderRepository.findAllOrdersWithDetails().stream()
                .map(order -> {
                    List<BookBriefDto> bookDtos = order.getClassEntity().getBooks().stream()
                            .map(book -> BookBriefDto.builder()
                                    .title(book.getTitle())
                                    .author(book.getAuthor())
                                    .subject(book.getSubject())
                                    .build())
                            .toList();

                    return OrderForPublisherDto.builder()
                            .orderId(order.getOrderId())
                            .customerName(order.getCustomer().getUser().getName())
                            .schoolName(order.getClassEntity().getSchool().getName())
                            .className(order.getClassEntity().getClassName())
                            .status(order.getStatus().name())
                            .books(bookDtos)
                            .build();
                })
                .toList();
    }

    @Override
    @Transactional
    public ApiResponse<PublisherProfileResponseDto> updatePublisherProfile(String email, PublisherProfileUpdateRequest request) {
        Publisher publisher = (Publisher) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Publisher not found"));

        publisher.setName(request.getName());
        Publisher updated = (Publisher) userRepository.save(publisher);

        PublisherProfileResponseDto dto = PublisherProfileResponseDto.builder()
                .id(updated.getId())
                .name(updated.getName())
                .email(updated.getEmail())
                .build();

        return ApiResponse.<PublisherProfileResponseDto>builder()
                .message("Publisher profile updated successfully.")
                .data(dto)
                .build();
    }


    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
    @Override
    public void deletePublisher(Long publisherId) {
        publisherRepository.deleteById(publisherId);
    }
    @Override
    @Transactional
    public void deleteOwnPublisher(String email) {
        Publisher publisher = (Publisher) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Publisher not found"));
        publisherRepository.deleteById(publisher.getId());
        userRepository.deleteById(publisher.getId());
    }


}
