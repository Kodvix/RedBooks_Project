package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.ClassEntity;
import com.org.kodvix.redbooks.dao.School;
import com.org.kodvix.redbooks.dto.ClassRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClassMapper {
    private final BookMapper bookMapper;

    public ClassMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public ClassEntity toDao(ClassRequest request, School school) {
        return ClassEntity.builder()
                .className(request.getClassName())
                .school(school)
                .build();
    }

//    public ClassRequest toDto(ClassEntity entity) {
//        ClassRequest dto = new ClassRequest();
//
//        dto.setClassName(entity.getClassName());
//        return dto;
//    }
}