package com.org.kodvix.redbooks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.kodvix.redbooks.dao.SchoolDao;
import com.org.kodvix.redbooks.dto.SchoolDto;

public interface SchoolRepository extends JpaRepository<SchoolDao, Long> {

//    public Optional<SchoolDto> findBySchoolEmail(String email);
//    public boolean existsBySchoolEmail(String email);
}
