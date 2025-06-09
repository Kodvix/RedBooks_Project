package com.org.kodvix.redbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.kodvix.redbooks.dao.SchoolClassDao;

import java.time.LocalDate;

public interface SchoolClassRepository extends JpaRepository<SchoolClassDao,Long>{

    boolean existsByClassNameAndSchool_SchoolIdAndAcademicYearStartAndAcademicYearEnd(
            String className, Long schoolId, LocalDate academicYearStart, LocalDate academicYearEnd);
}
