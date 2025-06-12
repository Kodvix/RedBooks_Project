package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}
