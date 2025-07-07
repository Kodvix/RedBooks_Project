package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.exception.*;
import com.org.kodvix.redbooks.exception.ClassNotFoundCustomException;
import com.org.kodvix.redbooks.mapper.BookMapper;
import com.org.kodvix.redbooks.mapper.ClassMapper;
import com.org.kodvix.redbooks.mapper.SchoolMapper;
import com.org.kodvix.redbooks.repository.BookRepository;
import com.org.kodvix.redbooks.repository.ClassEntityRepository;
import com.org.kodvix.redbooks.repository.UserRepository;
import com.org.kodvix.redbooks.service.SchoolService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final BookRepository bookRepository;
    private final ClassEntityRepository classRepository;
    private final UserRepository userRepository;


    private final BookMapper bookMapper;
    private final ClassMapper classMapper;
    private final SchoolMapper schoolMapper;


    @Override
    @Transactional
    public ApiResponse<BookResponseDto> addBook(BookRequest request, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        List<Book> existingBooks = bookRepository.findBySchoolId(school.getId());

        for (Book b : existingBooks) {
            boolean match = b.getTitle().equalsIgnoreCase(request.getTitle()) &&
                    b.getAuthor().equalsIgnoreCase(request.getAuthor()) &&
                    b.getSubject().equalsIgnoreCase(request.getSubject());

            if (match) {
                String classNames = b.getClasses().stream()
                        .map(ClassEntity::getClassName)
                        .collect(Collectors.joining(", "));

                String message = String.format(
                        "Book [ID: %d, Title: '%s', Author: '%s'] already exists in the school's catalog.",
                        b.getBookId(), b.getTitle(), b.getAuthor()
                );

                if (!classNames.isEmpty()) {
                    message += " It is assigned to class(es): " + classNames;
                }

                throw new DuplicateBookException(message);
            }
        }

        Book book = bookMapper.toDao(request);
        book.setSchool(school);
        book = bookRepository.save(book);

        BookResponseDto responseDto = BookResponseDto.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .subject(book.getSubject())
                .school(SchoolSummaryDto.builder()
                        .schoolId(school.getId())
                        .name(school.getName())
                        .build())
                .assignedToClasses(List.of())
                .isAssigned(false)
                .build();

        return ApiResponse.<BookResponseDto>builder()
                .message("Book added successfully.")
                .data(responseDto)
                .build();
    }

    public ClassEntity addClass(ClassRequest request, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));


        boolean exists = classRepository.findBySchoolId(school.getId()).stream()
                .anyMatch(c -> c.getClassName().equalsIgnoreCase(request.getClassName()));

        if (exists) {
            throw new DuplicateClassException("Class '" + request.getClassName() + "' already exists in your school.");
        }
        ClassEntity classEntity = classMapper.toDao(request, school);
        return classRepository.save(classEntity);
    }



    @Override
    @Transactional
    public ApiResponse<ClassBookAssignmentResponse> assignBooksToClass(Long classId, AssignBooksRequest request, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundCustomException("Class not found"));

        if (!classEntity.getSchool().getId().equals(school.getId())) {
            throw new InvalidClassAccessException("Class does not belong to your school");
        }

        List<BookAssignmentDto> requestedBooks = request.getBooks();
        List<Long> requestedBookIds = requestedBooks.stream()
                .map(BookAssignmentDto::getBookId)
                .toList();

        List<Book> books = bookRepository.findAllById(requestedBookIds);
        Set<Long> foundBookIds = books.stream().map(Book::getBookId).collect(Collectors.toSet());

        // Validate missing IDs
        List<Long> missingBookIds = requestedBookIds.stream()
                .filter(id -> !foundBookIds.contains(id))
                .toList();
        if (!missingBookIds.isEmpty()) {
            throw new BookNotFoundException("Book IDs not found: " + missingBookIds);
        }

        // Validate ownership
        List<Book> invalidBooks = books.stream()
                .filter(book -> !book.getSchool().getId().equals(school.getId()))
                .toList();
        if (!invalidBooks.isEmpty()) {
            String invalidList = invalidBooks.stream()
                    .map(b -> b.getBookId() + " ('" + b.getTitle() + "')")
                    .collect(Collectors.joining(", "));
            throw new BookOwnershipMismatchException("The following books do not belong to your school: " + invalidList);
        }

        // Validate title match
        for (BookAssignmentDto dto : requestedBooks) {
            Book matchedBook = books.stream()
                    .filter(b -> b.getBookId().equals(dto.getBookId()))
                    .findFirst()
                    .orElseThrow();

            if (!matchedBook.getTitle().equalsIgnoreCase(dto.getTitle())) {
                throw new BookTitleMismatchException("Title mismatch for Book ID " + dto.getBookId()
                        + ". Expected: '" + matchedBook.getTitle() + "', but received: '" + dto.getTitle() + "'");
            }
        }

        // Check for already assigned books
        Set<Long> alreadyAssigned = classEntity.getBooks().stream()
                .map(Book::getBookId)
                .collect(Collectors.toSet());

        List<Book> duplicates = books.stream()
                .filter(book -> alreadyAssigned.contains(book.getBookId()))
                .toList();
        if (!duplicates.isEmpty()) {
            String dupTitles = duplicates.stream()
                    .map(book -> "'" + book.getTitle() + "' by " + book.getAuthor())
                    .collect(Collectors.joining(", "));
            throw new BookAlreadyAssignedException("Already assigned to class '" +
                    classEntity.getClassName() + "': " + dupTitles);
        }

        // Assign and save
        classEntity.getBooks().addAll(books);
        classRepository.save(classEntity);

        List<ClassBookAssignmentResponse.BookSummary> assigned = books.stream()
                .map(book -> ClassBookAssignmentResponse.BookSummary.builder()
                        .bookId(book.getBookId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .build())
                .toList();

        return ApiResponse.<ClassBookAssignmentResponse>builder()
                .message("Books assigned successfully.")
                .data(ClassBookAssignmentResponse.builder()
                        .schoolId(school.getId())
                        .classId(classEntity.getClassId())
                        .className(classEntity.getClassName())
                        .assignedBooks(assigned)
                        .build())
                .build();
    }

//    @Override
//    public List<ClassEntity> getClassesBySchool(String schoolEmail) {
//        School school = (School) userRepository.findByEmail(schoolEmail)
//                .orElseThrow(() -> new UsernameNotFoundException("School not found"));
//        return classRepository.findBySchoolId(school.getId());
//    }


    @Override
    public SchoolClassBookOverviewResponse getClassesForSchool(Long schoolId) {
        School school = (School) userRepository.findById(schoolId)
                .filter(u -> u instanceof School)
                .map(School.class::cast)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        List<ClassWithBookNamesDto> classDtos = classRepository.findBySchoolId(schoolId).stream()
                .map(classEntity -> ClassWithBookNamesDto.builder()
                        .classId(classEntity.getClassId())
                        .className(classEntity.getClassName())
                        .bookAssigned(classEntity.getBooks().stream()
                                .map(Book::getTitle)
                                .toList())
                        .build())
                .toList();

        return SchoolClassBookOverviewResponse.builder()
                .schoolId(school.getId())
                .name(school.getName())
                .classes(classDtos)
                .build();
    }
    @Override
    public SchoolBooksWithClassesResponseDto getBooksWithAssignedClasses(Long schoolId) {
        School school = userRepository.findById(schoolId)
                .filter(u -> u instanceof School)
                .map(School.class::cast)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        List<BookWithClassNamesDto> bookDtos = bookRepository.findBySchoolId(schoolId).stream()
                .map(book -> {
                    List<String> classNames = book.getClasses().stream()
                            .map(ClassEntity::getClassName)
                            .toList();

                    return BookWithClassNamesDto.builder()
                            .bookId(book.getBookId())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .assignedToClasses(classNames)
                            .isAssigned(!classNames.isEmpty())
                            .build();
                })
                .toList();

        SchoolSummaryDto schoolDto = new SchoolSummaryDto(school.getId(), school.getName());
        return new SchoolBooksWithClassesResponseDto(schoolDto, bookDtos);
    }


    @Override
    public SchoolBooksResponseDto getBooksForSchool(Long schoolId) {
        School school = (School) userRepository.findById(schoolId)
                .filter(u -> u instanceof School)
                .map(School.class::cast)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        List<BookWithClassNamesDto> bookDtos = bookRepository.findBySchoolId(schoolId).stream()
                .map(book -> {
                    List<String> classNames = book.getClasses().stream()
                            .map(ClassEntity::getClassName)
                            .toList();

                    return BookWithClassNamesDto.builder()
                            .bookId(book.getBookId())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .assignedToClasses(classNames)
                            .isAssigned(!classNames.isEmpty())
                            .build();
                })
                .toList();

        SchoolSummaryDto schoolDto = new SchoolSummaryDto(school.getId(), school.getName());

        return new SchoolBooksResponseDto(schoolDto, bookDtos);
    }


    //    @Override
//    public List<Book> getBooksBySchool(String schoolEmail) {
//        School school = (School) userRepository.findByEmail(schoolEmail)
//                .orElseThrow(() -> new UsernameNotFoundException("School not found"));
//        return bookRepository.findBySchoolId(school.getId());
//    }
    @Override
    public List<ClassEntity> getClassesBySchoolId(Long schoolId) {
        return classRepository.findBySchoolId(schoolId);
    }

    @Override
    public List<Book> getBooksBySchoolId(Long schoolId) {
        return bookRepository.findBySchoolId(schoolId);
    }

    @Override
    @Transactional
    public ApiResponse<SchoolProfileResponseDto> updateSchoolProfile(String email, SchoolProfileUpdateRequest request) {
        School school = (School) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        school.setName(request.getName());
        School updated = (School) userRepository.save(school);

        SchoolProfileResponseDto dto = new SchoolProfileResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getEmail()
        );

        return ApiResponse.<SchoolProfileResponseDto>builder()
                .message("School profile updated successfully.")
                .data(dto)
                .build();
    }

    @Override
    public List<SchoolResponseDto> getAllSchools() {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof School)
                .map(School.class::cast)
                .map(schoolMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ApiResponse<ClassUpdateResponseDto> updateClass(Long classId, ClassRequest request, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundCustomException("Class not found"));

        if (!classEntity.getSchool().getId().equals(school.getId())) {
            throw new ClassOwnershipException("Class does not belong to your school");
        }

        classEntity.setClassName(request.getClassName());
        ClassEntity updated = classRepository.save(classEntity);

        ClassUpdateResponseDto dto = ClassUpdateResponseDto.builder()
                .classId(updated.getClassId())
                .className(updated.getClassName())
                .schoolId(school.getId())
                .schoolName(school.getName())
                .build();

        return ApiResponse.<ClassUpdateResponseDto>builder()
                .message("Class updated successfully.")
                .data(dto)
                .build();
    }


    @Override
    @Transactional
    public ApiResponse<BookUpdateResponseDto> updateBook(Long bookId, BookRequest request, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new SchoolNotFoundException("School not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (!book.getSchool().getId().equals(school.getId())) {
            throw new BookOwnershipMismatchException("Book does not belong to your school");
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setSubject(request.getSubject());

        Book updated = bookRepository.save(book);

        BookUpdateResponseDto responseDto = BookUpdateResponseDto.builder()
                .bookId(updated.getBookId())
                .title(updated.getTitle())
                .author(updated.getAuthor())
                .subject(updated.getSubject())
                .schoolId(school.getId())
                .schoolName(school.getName())
                .build();

        return ApiResponse.<BookUpdateResponseDto>builder()
                .message("Book updated successfully.")
                .data(responseDto)
                .build();
    }

    @Override
    public void deleteSchool(Long schoolId) {
        User user = userRepository.findById(schoolId)
                .orElseThrow(() -> new SchoolNotFoundException("School with ID " + schoolId + " not found"));
        if (!Role.SCHOOL.equals(user.getRole())) {
            throw new SchoolNotFoundException("User with ID " + schoolId + " is not a school");
        }
        userRepository.deleteById(schoolId);
    }
    @Override
    public void deleteBook(Long bookId, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (!book.getSchool().getId().equals(school.getId())) {
            throw new BookOwnershipMismatchException("Book does not belong to your school");
        }

        bookRepository.deleteById(bookId);
    }
    @Override
    public void deleteClass(Long classId, String schoolEmail) {
        School school = (School) userRepository.findByEmail(schoolEmail)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundCustomException("Class not found"));

        if (!classEntity.getSchool().getId().equals(school.getId())) {
            throw new RuntimeException("Class does not belong to your school");
        }

        classRepository.deleteById(classId);
    }
    @Override
    @Transactional
    public void deleteOwnSchool(String email) {
        School school = (School) userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("School not found"));
        userRepository.deleteById(school.getId());
    }



}
