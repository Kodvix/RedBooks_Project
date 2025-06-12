package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dto.BookRequest;
import com.org.kodvix.redbooks.dto.ClassRequest;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.mapper.BookMapper;
import com.org.kodvix.redbooks.mapper.ClassMapper;
import com.org.kodvix.redbooks.repository.BookRepository;
import com.org.kodvix.redbooks.repository.ClassEntityRepository;
import com.org.kodvix.redbooks.repository.UserRepository;
import com.org.kodvix.redbooks.service.SchoolService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final BookRepository bookRepository;
    private final ClassEntityRepository classRepository;
    private final UserRepository userRepository;


    private final BookMapper bookMapper;
    private final ClassMapper classMapper;

    @Override
    @Transactional
    public ClassEntity addClass(ClassRequest request, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));


        ClassEntity classEntity = classMapper.toDao(request, school);
        return classRepository.save(classEntity);
    }

    @Override
    @Transactional
    public Book addBook(BookRequest request, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));


        Book book = bookMapper.toDao(request);
        book.setSchool(school); // school must be set separately
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public ClassEntity assignBooksToClass(Long classId, List<Long> bookIds, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!classEntity.getSchool().getId().equals(school.getId())) {
            throw new RuntimeException("Class does not belong to your school");
        }

        List<Book> books = bookRepository.findAllById(bookIds);
        for (Book book : books) {
            if (!book.getSchool().getId().equals(school.getId())) {
                throw new RuntimeException("One or more books do not belong to your school");
            }
        }

        classEntity.getBooks().clear();
        classEntity.getBooks().addAll(books);
        return classRepository.save(classEntity);
    }

    @Override
    public List<ClassEntity> getClassesBySchool(String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));
        return classRepository.findBySchoolId(school.getId());
    }

    @Override
    public List<Book> getBooksBySchool(String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));
        return bookRepository.findBySchoolId(school.getId());
    }
}
