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
    public ResponseEntity<ClassEntity> addClass(@AuthenticationPrincipal UserDetails user,
                                                @Valid @RequestBody ClassRequest request) {
        return ResponseEntity.ok(schoolService.addClass(request, user.getUsername()));
    }

    @Operation(summary = "Add a new book to the school’s catalog")
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@AuthenticationPrincipal UserDetails user,
                                        @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(schoolService.addBook(request, user.getUsername()));
    }

    @Operation(summary = "Assign books to a class")
    @PostMapping("/classes/{classId}/books")
    public ResponseEntity<ClassEntity> assignBooksToClass(@AuthenticationPrincipal UserDetails user,
                                                          @PathVariable Long classId,
                                                          @RequestBody List<Long> bookIds) {
        return ResponseEntity.ok(schoolService.assignBooksToClass(classId, bookIds, user.getUsername()));
    }

    @Operation(summary = "Get all classes created by the school")
    @GetMapping("/classes")
    public ResponseEntity<List<ClassEntity>> getClasses(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(schoolService.getClassesBySchool(user.getUsername()));
    }

    @Operation(summary = "Get all books added by the school")
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(schoolService.getBooksBySchool(user.getUsername()));
    }
}
