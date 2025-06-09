package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.BookDao;
import com.org.kodvix.redbooks.dao.PublisherDao;
import com.org.kodvix.redbooks.dto.BookDto;
import com.org.kodvix.redbooks.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class BookMapper {

    private final PublisherRepository publisherRepository;

    @Autowired
    public BookMapper(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public BookDao toDao(BookDto dto) throws IOException {
        if (dto == null) return null;

        BookDao dao = new BookDao();
        dao.setId(dto.getId());
        dao.setTitle(dto.getTitle());
        dao.setAuthor(dto.getAuthor());
        dao.setIsbn(dto.getIsbn());
        dao.setPublicationYear(dto.getPublicationYear());
        dao.setStockQuantity(dto.getStockQuantity());
        dao.setBookPrice(dto.getBookPrice());
        dao.setCategory(dto.getCategory());
        dao.setLanguage(dto.getLanguage());
        dao.setNumberOfPages(dto.getNumberOfPages());
        dao.setEdition(dto.getEdition());

        if (dto.getPublisherId() != null) {
            PublisherDao publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + dto.getPublisherId()));
            dao.setPublisherRef(publisher);
        } else {
            dao.setPublisherRef(null);
        }

        MultipartFile orderDocFile = dto.getOrderDocument();
        if (orderDocFile != null && !orderDocFile.isEmpty()) {
            dao.setOrderDocument(orderDocFile.getBytes());
            dao.setOrderDocumentName(orderDocFile.getOriginalFilename());
            dao.setOrderDocumentType(orderDocFile.getContentType());
        } else {
            dao.setOrderDocument(null);
            dao.setOrderDocumentName(null);
            dao.setOrderDocumentType(null);
        }

        return dao;
    }

    public BookDto toDto(BookDao dao) {
        if (dao == null) return null;

        BookDto dto = new BookDto();
        dto.setId(dao.getId());
        dto.setTitle(dao.getTitle());
        dto.setAuthor(dao.getAuthor());
        dto.setIsbn(dao.getIsbn());
        dto.setPublicationYear(dao.getPublicationYear());
        dto.setStockQuantity(dao.getStockQuantity());
        dto.setBookPrice(dao.getBookPrice());
        dto.setCategory(dao.getCategory());
        dto.setLanguage(dao.getLanguage());
        dto.setNumberOfPages(dao.getNumberOfPages());
        dto.setEdition(dao.getEdition());

        if (dao.getPublisherRef() != null) {
            dto.setPublisherId(dao.getPublisherRef().getId());
        } else {
            dto.setPublisherId(null);
        }

        // Note: We do not map orderDocument MultipartFile back from byte[],
        // because MultipartFile is for incoming HTTP upload only.

        return dto;
    }
}
