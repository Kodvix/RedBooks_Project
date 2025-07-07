package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity> findBySchoolId(Long schoolId);

    Optional<ClassEntity> findByClassNameIgnoreCaseAndSchool_NameIgnoreCase(String className, String schoolName);
}
