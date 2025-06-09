package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dao.BookDao;
import com.org.kodvix.redbooks.dao.PublisherDao;
import com.org.kodvix.redbooks.dto.BookDto;
import com.org.kodvix.redbooks.dto.PublisherDto;
import com.org.kodvix.redbooks.exception.ResourceNotFoundException;
import com.org.kodvix.redbooks.mapper.BookMapper;
import com.org.kodvix.redbooks.mapper.PublisherMapper;
import com.org.kodvix.redbooks.repository.BookRepository;
import com.org.kodvix.redbooks.repository.PublisherRepository;
import com.org.kodvix.redbooks.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    public PublisherDto updateProfile(String publisherName, PublisherDto profileDto) {
        PublisherDao existing = publisherRepository.findFirstByPublisherName(publisherName)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: " + publisherName));

        // Map relevant fields from DTO to entity
        if (profileDto.getEmail() != null) {
            existing.setEmail(profileDto.getEmail());
        }
        if (profileDto.getPhoneNumber() != null) {
            existing.setPhoneNumber(profileDto.getPhoneNumber());
        }
        if (profileDto.getAddress() != null) {
            existing.setAddress(profileDto.getAddress());
        }
        // Add other profile fields as needed...

        PublisherDao updated = publisherRepository.save(existing);
        return publisherMapper.toDto(updated);
    }

    @Override
    public PublisherDto viewProfile(String publisherName) {
        PublisherDao existing = publisherRepository.findFirstByPublisherName(publisherName)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: " + publisherName));
        return publisherMapper.toDto(existing);
    }
    @Override
    public List<BookDto> getBooksByPublisher(String publisherName) {
        PublisherDao publisher = publisherRepository.findFirstByPublisherName(publisherName)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: " + publisherName));
        List<BookDao> books = bookRepository.findByPublisherRef(publisher);
        return books.stream().map(bookMapper::toDto).toList();
    }
    @Override
    public Object viewSalesReports(String publisherName) {
        throw new UnsupportedOperationException("Sales reports not implemented yet");
    }

    @Override
    public void respondToReview(Long reviewId, String response) {
        throw new UnsupportedOperationException("Respond to review not implemented yet");
    }

    @Override
    public Object viewNotifications(String publisherName) {
        throw new UnsupportedOperationException("Notifications not implemented yet");
    }
}
