package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dao.Book;
import com.org.kodvix.redbooks.dao.ClassEntity;
import com.org.kodvix.redbooks.dao.School;
import com.org.kodvix.redbooks.dto.*;

import java.util.List;

public interface SchoolService {
    ClassEntity addClass(ClassRequest request, String schoolEmail);

    ApiResponse<BookResponseDto> addBook(BookRequest request, String schoolEmail);

    //ApiResponse<ClassBookAssignmentResponse> assignBooksToClass(Long classId, List<Long> bookIds, String schoolEmail);//commented for bookId and bookTitle

    //ApiResponse<ClassBookAssignmentResponse> assignBooksToClass(Long classId, List<BookAssignmentDto> books, String schoolEmail);

    ApiResponse<ClassBookAssignmentResponse> assignBooksToClass(Long classId, AssignBooksRequest request, String schoolEmail);
    //School updateSchoolProfile(String email, SchoolProfileUpdateRequest request);
    public ApiResponse<SchoolProfileResponseDto> updateSchoolProfile(String email, SchoolProfileUpdateRequest request) ;

    List<ClassEntity> getClassesBySchoolId(Long schoolId);

    List<Book> getBooksBySchoolId(Long schoolId);

    List<SchoolResponseDto> getAllSchools();

    //ClassEntity updateClass(Long classId, ClassRequest request, String schoolEmail);

    public ApiResponse<ClassUpdateResponseDto> updateClass(Long classId, ClassRequest request, String schoolEmail) ;

   // Book updateBook(Long bookId, BookRequest request, String schoolEmail);

    public ApiResponse<BookUpdateResponseDto> updateBook(Long bookId, BookRequest request, String schoolEmail) ;

    void deleteSchool(Long schoolId);

    void deleteBook(Long bookId, String schoolEmail);

    void deleteClass(Long classId, String schoolEmail);

    void deleteOwnSchool(String email);

    SchoolClassBookOverviewResponse getClassesForSchool(Long schoolId);

    SchoolBooksResponseDto getBooksForSchool(Long schoolId);

    SchoolBooksWithClassesResponseDto getBooksWithAssignedClasses(Long schoolId);
}
