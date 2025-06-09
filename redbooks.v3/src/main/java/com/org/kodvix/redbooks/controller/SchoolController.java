package com.org.kodvix.redbooks.controller;
import com.org.kodvix.redbooks.dto.PublisherDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.org.kodvix.redbooks.dao.SchoolDao;
import com.org.kodvix.redbooks.dto.SchoolDto;
import com.org.kodvix.redbooks.serviceImp.SchoolServiceImpl;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "School API", description = "Manage school registrations and details")
@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Autowired
    private SchoolServiceImpl schoolService;

//    @Operation(summary = "Register a new school")
//    @PostMapping("/register")
//    public ResponseEntity<SchoolDto> registerSchool(@Valid @RequestBody SchoolDto school) {
//        return ResponseEntity.ok(schoolService.registerSchool(school));
//    }

    @Operation(summary = "Get school details")
    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolDto> getSchoolDetails(@PathVariable("schoolId") Long id) {
        return ResponseEntity.ok(schoolService.getSchoolDetails(id));
    }

    @Operation(summary = "Update school details")
    @PutMapping("/update/{schoolId}")
    public ResponseEntity<SchoolDto> updateSchoolDetails(@PathVariable("schoolId") Long id, @RequestBody SchoolDto school) {
        return ResponseEntity.ok(schoolService.updateSchoolDetails(id,school));
    }

    @Operation(summary = "Delete a school")
    @DeleteMapping("/{schoolId}")
    public ResponseEntity<String> deleteSchoolById(@PathVariable("schoolId") Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok("School Deleted SuccessFully");
    }

    @Operation(summary = "Recommend publishers for a school")
    @PostMapping("/{schoolId}/recommend-publishers")
    public ResponseEntity<SchoolDto> recommendPublishers(
            @PathVariable("schoolId") Long schoolId,
            @RequestBody List<Long> publisherIds) {
        return ResponseEntity.ok(schoolService.recommendPublishers(schoolId, publisherIds));
    }
    @Operation(summary = "Recommend publishers for a school by Names")
    @PostMapping("/{schoolId}/recommend-publishers-by-names")
    public ResponseEntity<SchoolDto> recommendPublishersByNames(
            @PathVariable("schoolId") Long schoolId,
            @RequestBody List<String> publisherNames) {
        return ResponseEntity.ok(schoolService.recommendPublishersByNames(schoolId, publisherNames));
    }

    @Operation(summary = "Get recommended publishers for a school")
    @GetMapping("/{schoolId}/recommended-publishers")
    public ResponseEntity<List<PublisherDto>> getRecommendedPublishers(@PathVariable("schoolId") Long schoolId) {
        return ResponseEntity.ok(schoolService.getRecommendedPublishers(schoolId));
    }



}