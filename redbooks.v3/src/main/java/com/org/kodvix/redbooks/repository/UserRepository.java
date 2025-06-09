package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dao.SchoolDao;
import com.org.kodvix.redbooks.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDao, Long> {
    Optional<UserDao> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByRoleAndSchool(Role role, SchoolDao school);
}
