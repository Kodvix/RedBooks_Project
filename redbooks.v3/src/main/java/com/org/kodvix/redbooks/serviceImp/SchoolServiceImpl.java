package com.org.kodvix.redbooks.serviceImp;

import java.util.List;
import java.util.stream.Collectors;

import com.org.kodvix.redbooks.dao.PublisherDao;
import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dto.PublisherDto;
import com.org.kodvix.redbooks.repository.PublisherRepository;
import com.org.kodvix.redbooks.repository.UserRepository;
import com.org.kodvix.redbooks.service.SchoolService;
import com.org.kodvix.redbooks.dao.SchoolDao;
import com.org.kodvix.redbooks.dto.SchoolClassDto;
import com.org.kodvix.redbooks.dto.SchoolDto;
import com.org.kodvix.redbooks.exception.ResourceNotFoundException;
import com.org.kodvix.redbooks.repository.SchoolRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolRepository schoolRepo;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public SchoolDto getSchoolDetails(Long id) {
        SchoolDao school = schoolRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
        return convertToDto(school);
    }

    @Override
    public void deleteSchool(Long schoolId) {
        SchoolDao school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + schoolId));

        boolean hasAdmin = userRepository.existsByRoleAndSchool(Role.SCHOOL_ADMIN, school);
        if (hasAdmin) {
            throw new IllegalStateException("Cannot delete school. A SCHOOL_ADMIN is still linked to this school.");
        }

        schoolRepo.deleteById(schoolId);
    }

    @Override
    public SchoolDto updateSchoolDetails(Long id, SchoolDto updateSchool) {
        SchoolDao school = schoolRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));

        school.setSchoolName(updateSchool.getSchoolName());
        school.setBoard(updateSchool.getBoard());
        school.setCategory(updateSchool.getCategory());
        school.setContact(updateSchool.getContact());
        school.setCreatedAt(updateSchool.getCreatedAt());
        school.setDescription(updateSchool.getDescription());
        school.setMedium(updateSchool.getMedium());
        school.setSchoolType(updateSchool.getSchoolType());
        school.setSocialMediaUrl(updateSchool.getSocialMediaUrl());
        school.setUpdatedAt(updateSchool.getUpdatedAt());

        // Set recommended publishers
        if (updateSchool.getRecommendedPublishers() != null) {
            school.setRecommendedPublishers(
                    updateSchool.getRecommendedPublishers().stream()
                            .map(pubDto -> publisherRepository.findById(pubDto.getId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + pubDto.getId())))
                            .collect(Collectors.toList())
            );
        }

        return convertToDto(schoolRepo.save(school));
    }

    @Override
    public SchoolDto addClass(SchoolClassDto schoolClass) {
        return null; // Placeholder
    }

    private SchoolDao convertToDao(SchoolDto schoolDto) {
        SchoolDao school = mapper.map(schoolDto, SchoolDao.class);

        if (schoolDto.getRecommendedPublishers() != null) {
            school.setRecommendedPublishers(
                    schoolDto.getRecommendedPublishers().stream()
                            .map(pubDto -> publisherRepository.findById(pubDto.getId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + pubDto.getId())))
                            .collect(Collectors.toList())
            );
        }

        return school;
    }

    private SchoolDto convertToDto(SchoolDao schoolDao) {
        SchoolDto dto = mapper.map(schoolDao, SchoolDto.class);

        if (schoolDao.getRecommendedPublishers() != null) {
            dto.setRecommendedPublishers(
                    schoolDao.getRecommendedPublishers().stream()
                            .map(pub -> {
                                PublisherDto p = new PublisherDto();
                                p.setId(pub.getId());
                                p.setPublisherName(pub.getPublisherName());
                                p.setEmail(pub.getEmail());
                                p.setPhoneNumber(pub.getPhoneNumber());
                                p.setAddress(pub.getAddress());
                                return p;
                            }).collect(Collectors.toList())
            );
        }

        return dto;
    }

    @Override
    public SchoolDto recommendPublishers(Long schoolId, List<Long> publisherIds) {
        SchoolDao school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        List<PublisherDao> publishers = publisherRepository.findAllById(publisherIds);
        school.setRecommendedPublishers(publishers); // override or use addAll if needed

        return convertToDto(schoolRepo.save(school));
    }

    @Override
    public List<PublisherDto> getRecommendedPublishers(Long schoolId) {
        SchoolDao school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found"));

        return school.getRecommendedPublishers()
                .stream()
                .map(publisher -> mapper.map(publisher, PublisherDto.class))
                .toList();
    }

    @Override
    public SchoolDto recommendPublishersByNames(Long schoolId, List<String> publisherNames) {
        SchoolDao school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + schoolId));

        // Use findFirstByPublisherName which returns Optional<PublisherDao>
        List<PublisherDao> publishers = publisherNames.stream()
                .map(name -> publisherRepository.findFirstByPublisherName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with name: " + name)))
                .collect(Collectors.toList());

        school.setRecommendedPublishers(publishers);

        return convertToDto(schoolRepo.save(school));
    }
}
