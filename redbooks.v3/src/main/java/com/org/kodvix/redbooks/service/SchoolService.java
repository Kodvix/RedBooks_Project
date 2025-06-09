package com.org.kodvix.redbooks.service;

import java.util.List;
import java.util.Optional;

import com.org.kodvix.redbooks.dto.PublisherDto;
import com.org.kodvix.redbooks.dto.SchoolClassDto;
import com.org.kodvix.redbooks.dto.SchoolDto;

public interface SchoolService {

   // public SchoolDto registerSchool(SchoolDto schoolDto) ;

//	public SchoolDto loginSchool(String userName, String password);

    public SchoolDto getSchoolDetails(Long id) ;

// public Optional<SchoolDto> getSchoolDetailsByEmail(String email);

   // boolean existsBySchoolEmail(String email);

    public void deleteSchool(Long Id);

    public SchoolDto updateSchoolDetails(Long id, SchoolDto school);

    public SchoolDto addClass(SchoolClassDto schoolClass);


    SchoolDto recommendPublishers(Long schoolId, List<Long> publisherIds);


    List<PublisherDto> getRecommendedPublishers(Long schoolId);

    SchoolDto recommendPublishersByNames(Long schoolId, List<String> publisherNames);


}
