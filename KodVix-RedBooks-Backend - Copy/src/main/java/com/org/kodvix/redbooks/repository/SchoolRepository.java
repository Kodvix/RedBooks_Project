package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.School;
import com.org.kodvix.redbooks.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<User> findByEmail(String email);
}
