package com.org.kodvix.redbooks.controller;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.service.SchoolService;
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
public class SchoolController {
    private final SchoolService schoolService;

    @PostMapping("/classes")
    public ResponseEntity<ClassEntity> addClass(@AuthenticationPrincipal UserDetails user,
                                                @Valid @RequestBody ClassRequest request) {
        return ResponseEntity.ok(schoolService.addClass(request, user.getUsername()));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@AuthenticationPrincipal UserDetails user,
                                        @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(schoolService.addBook(request, user.getUsername()));
    }
    @PostMapping("/classes/{classId}/books")
    public ResponseEntity<ClassEntity> assignBooksToClass(@AuthenticationPrincipal UserDetails user,
                                                          @PathVariable Long classId,
                                                          @RequestBody List<Long> bookIds) {
        return ResponseEntity.ok(schoolService.assignBooksToClass(classId, bookIds, user.getUsername()));
    }

    @GetMapping("/classes")
    public ResponseEntity<List<ClassEntity>> getClasses(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(schoolService.getClassesBySchool(user.getUsername()));
    }
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(schoolService.getBooksBySchool(user.getUsername()));
    }
}
