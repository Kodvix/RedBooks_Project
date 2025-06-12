package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
