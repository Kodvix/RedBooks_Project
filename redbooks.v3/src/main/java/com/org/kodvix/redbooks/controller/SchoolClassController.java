package com.org.kodvix.redbooks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.kodvix.redbooks.dto.SchoolClassDto;
import com.org.kodvix.redbooks.service.SchoolClassService;

@Tag(name = "School Class API", description = "Manage classes within schools")
@RestController
@RequestMapping("/api/school/class")
public class SchoolClassController {

    @Autowired
    public SchoolClassService classService;

    @Operation(summary = "Add a new class")
    @PostMapping("/add")
    public ResponseEntity<SchoolClassDto> createClass(@RequestBody SchoolClassDto schoolClass) {
        return ResponseEntity.ok(classService.addClass(schoolClass));
    }

    @Operation(summary = "Get class details by ID")
    @GetMapping("/{classId}")
    public ResponseEntity<SchoolClassDto> getClassDetails(@PathVariable("classId") Long classId) {
        return ResponseEntity.ok(classService.getClass(classId));
    }

    @Operation(summary = "Update class details")
    @PutMapping("/update/{classId}")
    public ResponseEntity<SchoolClassDto> updateClassDetails(@PathVariable("classId") Long classId, @RequestBody SchoolClassDto classDto) {
        return ResponseEntity.ok(classService.updateClass(classId,classDto));
    }

    @Operation(summary = "Delete a class")
    @DeleteMapping("/delete/{classId}")
    public ResponseEntity<String> deleteClass(@PathVariable("classId") Long classId) {
        classService.deleteClass(classId);
        return ResponseEntity.ok("class deleted Successfully");
    }

}
