package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {
    List<ClassEntity> findBySchoolId(Long schoolId);
}
