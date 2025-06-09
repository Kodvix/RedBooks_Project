package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.PublisherDao;
import com.org.kodvix.redbooks.dto.PublisherDto;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherDto toDto(PublisherDao dao) {
        if (dao == null) return null;

        PublisherDto dto = new PublisherDto();
        dto.setId(dao.getId());
        dto.setPublisherName(dao.getPublisherName());
        dto.setEmail(dao.getEmail());
        dto.setPhoneNumber(dao.getPhoneNumber());
        dto.setAddress(dao.getAddress());
        return dto;
    }

    public PublisherDao toDao(PublisherDto dto) {
        if (dto == null) return null;

        PublisherDao dao = new PublisherDao();
        dao.setId(dto.getId());
        dao.setPublisherName(dto.getPublisherName());
        dao.setEmail(dto.getEmail());
        dao.setPhoneNumber(dto.getPhoneNumber());
        dao.setAddress(dto.getAddress());
        return dao;
    }
}
