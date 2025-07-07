package com.org.kodvix.redbooks.controller;

import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
@Tag(name = "School API", description = "Endpoints for school admin to manage classes and books")
public class SchoolController {

    private final SchoolService schoolService;

    @Operation(summary = "Add a new class")
    @PostMapping("/classes")
    public ResponseEntity<AddClassResponseDto> addClass(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody ClassRequest request) {

        ClassEntity createdClass = schoolService.addClass(request, user.getUsername());
        School school = createdClass.getSchool();

        AddClassResponseDto response = AddClassResponseDto.builder()
                .message("Class added successfully.")
                .classInfo(new ClassSummaryDto(createdClass.getClassId(), createdClass.getClassName()))
                .schoolInfo(new SchoolSummaryDto(school.getId(), school.getName()))
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new book to the school’s catalog")
    @PostMapping("/books")
    public ResponseEntity<ApiResponse<BookResponseDto>> addBook(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody BookRequest request) {

        ApiResponse<BookResponseDto> responseDto = schoolService.addBook(request, user.getUsername());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/classes/{classId}/books")
    @Operation(summary = "Assign books to a class")
    public ResponseEntity<ApiResponse<ClassBookAssignmentResponse>> assignBooksToClass(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long classId,
            @RequestBody AssignBooksRequest request) {

        ApiResponse<ClassBookAssignmentResponse> response =
                schoolService.assignBooksToClass(classId, request, user.getUsername());

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Update school profile (authenticated)")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<SchoolProfileResponseDto>> updateProfile(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody SchoolProfileUpdateRequest request) {
        return ResponseEntity.ok(schoolService.updateSchoolProfile(user.getUsername(), request));
    }

    @Operation(summary = "Get all registered schools (public)")
    @GetMapping("/show-all-schools")
    public ResponseEntity<List<SchoolResponseDto>> getAllSchools() {
        return ResponseEntity.ok(schoolService.getAllSchools());
    }

    @Operation(summary = "Update class details (name)")
    @PutMapping("/classes/{classId}")
    public ResponseEntity<ApiResponse<ClassUpdateResponseDto>> updateClass(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long classId,
            @Valid @RequestBody ClassRequest request) {

        ApiResponse<ClassUpdateResponseDto> response =
                schoolService.updateClass(classId, request, user.getUsername());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update book details (title, author, subject)")
    @PutMapping("/books/{bookId}")
    public ResponseEntity<ApiResponse<BookUpdateResponseDto>> updateBook(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long bookId,
            @Valid @RequestBody BookRequest request) {

        ApiResponse<BookUpdateResponseDto> response =
                schoolService.updateBook(bookId, request, user.getUsername());

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/books/{bookId}")
    @Operation(summary = "Delete a book from catalog (school only)")
    public ResponseEntity<String> deleteBook(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long bookId) {
        schoolService.deleteBook(bookId, user.getUsername());
        return ResponseEntity.ok("Book deleted successfully.");
    }

    @DeleteMapping("/classes/{classId}")
    @Operation(summary = "Delete a class (school only)")
    public ResponseEntity<String> deleteClass(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long classId) {
        schoolService.deleteClass(classId, user.getUsername());
        return ResponseEntity.ok("Class deleted successfully.");
    }

    @Operation(summary = "Delete own school profile (Authenticated School only)")
    @DeleteMapping("/delete-profile")
    public ResponseEntity<String> deleteOwnProfile(@AuthenticationPrincipal UserDetails user) {
        schoolService.deleteOwnSchool(user.getUsername());
        return ResponseEntity.ok("Your school account has been deleted successfully.");
    }

    @Operation(summary = "Get all classes for a school (public)")
    @GetMapping("/classes")
    public ResponseEntity<SchoolClassBookOverviewResponse> getClasses(
            @RequestParam Long schoolId) {
        return ResponseEntity.ok(schoolService.getClassesForSchool(schoolId));
    }

    @Operation(summary = "Get all books for a school (public)")
    @GetMapping("/books")
    public ResponseEntity<SchoolBooksResponseDto> getBooks(
            @RequestParam Long schoolId) {
        return ResponseEntity.ok(schoolService.getBooksForSchool(schoolId));
    }
}
