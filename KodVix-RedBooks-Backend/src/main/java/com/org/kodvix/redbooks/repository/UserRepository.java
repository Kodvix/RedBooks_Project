package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNameAndRole(String name, Role role);
}
