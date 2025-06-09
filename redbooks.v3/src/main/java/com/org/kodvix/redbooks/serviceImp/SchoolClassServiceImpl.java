package com.org.kodvix.redbooks.serviceImp;

import java.time.LocalDate;

import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dao.UserDao;
import com.org.kodvix.redbooks.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.org.kodvix.redbooks.dao.SchoolClassDao;
import com.org.kodvix.redbooks.dao.SchoolDao;
import com.org.kodvix.redbooks.dto.SchoolClassDto;
import com.org.kodvix.redbooks.exception.ResourceNotFoundException;
import com.org.kodvix.redbooks.repository.SchoolClassRepository;
import com.org.kodvix.redbooks.repository.SchoolRepository;
import com.org.kodvix.redbooks.service.SchoolClassService;

@Service
public class SchoolClassServiceImpl  implements SchoolClassService {

    @Autowired
    public SchoolClassRepository classRepo;

    @Autowired
    public SchoolRepository schoolRepo;

    @Autowired
    public ModelMapper mapper;

    @Autowired
    private UserServiceImpl userService;
    public SchoolClassDto addClass(SchoolClassDto schoolClass) {

        UserDao currentUser = userService.getLoggedInUser();

        if (currentUser.getRole() != Role.SCHOOL_ADMIN) {
            throw new AccessDeniedException("Only SCHOOL_ADMIN can add classes.");
        }

        if (currentUser.getSchool() == null || !currentUser.getSchool().getSchoolId().equals(schoolClass.getSchoolId())) {
            throw new AccessDeniedException("You can only add classes to your own school.");
        }
        SchoolDao school = schoolRepo.findById(schoolClass.getSchoolId()).orElseThrow(()-> new ResourceNotFoundException("id not found "));
        //schoolClass.setAcademicYear(LocalDate.now());//override -commented

//        if (schoolClass.getAcademicYear() == null) {
//            schoolClass.setAcademicYear(LocalDate.now());
//        }

        if (schoolClass.getAcademicYearStart() == null || schoolClass.getAcademicYearEnd() == null) {
            LocalDate now = LocalDate.now();
            schoolClass.setAcademicYearStart(LocalDate.of(now.getYear(), 6, 1)); // Example: June 1
            schoolClass.setAcademicYearEnd(LocalDate.of(now.getYear() + 1, 3, 31)); // Example: March 31
        }


        boolean exists = classRepo.existsByClassNameAndSchool_SchoolIdAndAcademicYearStartAndAcademicYearEnd(
                schoolClass.getClassName(),
                schoolClass.getSchoolId(),
                schoolClass.getAcademicYearStart(),
                schoolClass.getAcademicYearEnd()
        );

        if (exists) {
            throw new IllegalArgumentException("Class already exists for the given academic year.");
        }

        return convertToDto(classRepo.save(convertToDao(schoolClass)));
    }

    @Override
    public SchoolClassDto getClass(Long id) {
        UserDao currentUser = userService.getLoggedInUser();
        SchoolClassDao schoolClass =	classRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id not found"));
        if (currentUser.getRole() == Role.SCHOOL_ADMIN) {
            if (currentUser.getSchool() == null ||
                    !schoolClass.getSchool().getSchoolId().equals(currentUser.getSchool().getSchoolId())) {
                throw new AccessDeniedException("You can only view classes from your own school.");
            }
        }

        return convertToDto(schoolClass);
    }


    @Override
    public SchoolClassDto updateClass(Long classId, SchoolClassDto classDto) {

        UserDao currentUser = userService.getLoggedInUser();

        if (currentUser.getRole() != Role.SCHOOL_ADMIN) {
            throw new AccessDeniedException("Only SCHOOL_ADMIN can update classes.");
        }
        SchoolClassDao existing = classRepo.findById(classId)
                .orElseThrow(()-> new ResourceNotFoundException("class not found "));
        existing.setClassName(classDto.getClassName());
        if (classDto.getAcademicYearStart() != null) {
            existing.setAcademicYearStart(classDto.getAcademicYearStart());
        }

        if (classDto.getAcademicYearEnd() != null) {
            existing.setAcademicYearEnd(classDto.getAcademicYearEnd());
        }

        if (!existing.getSchool().getSchoolId().equals(currentUser.getSchool().getSchoolId())) {
            throw new AccessDeniedException("You can only update classes from your own school.");
        }

        boolean duplicateExists = classRepo.existsByClassNameAndSchool_SchoolIdAndAcademicYearStartAndAcademicYearEnd(
                classDto.getClassName(),
                currentUser.getSchool().getSchoolId(),
                classDto.getAcademicYearStart(),
                classDto.getAcademicYearEnd());

        if (duplicateExists && !existing.getClassName().equals(classDto.getClassName())) {
            throw new IllegalArgumentException("Another class with the same name and academic year already exists.");
        }
        existing.setClassName(classDto.getClassName());

        return convertToDto(classRepo.save(existing));
    }

    @Override
    public void deleteClass(Long classId) {
        UserDao currentUser = userService.getLoggedInUser();

        if (currentUser.getRole() != Role.SCHOOL_ADMIN) {
            throw new AccessDeniedException("Only SCHOOL_ADMIN can delete classes.");
        }
        SchoolClassDao school = classRepo.findById(classId).orElseThrow(()-> new ResourceNotFoundException("Class not found "));
        if (!school.getSchool().getSchoolId().equals(currentUser.getSchool().getSchoolId())) {
            throw new AccessDeniedException("You can only delete classes from your own school.");
        }
        classRepo.deleteById(classId);
    }

    private SchoolClassDao convertToDao(SchoolClassDto classDto) {
        return mapper.map(classDto, SchoolClassDao.class);
    }

    private SchoolClassDto convertToDto(SchoolClassDao classDao) {
        return mapper.map(classDao, SchoolClassDto.class);
    }


}
