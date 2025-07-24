package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_CustomerId(Long customerId);

    @Query("""
        SELECT DISTINCT o FROM Order o
        JOIN FETCH o.customer c
        JOIN FETCH c.user
        JOIN FETCH o.classEntity ce
        JOIN FETCH ce.school s
        LEFT JOIN FETCH ce.books b
    """)
    List<Order> findAllOrdersWithDetails(); // ✅ New correct method name

    @Query("""
        SELECT DISTINCT o FROM Order o
        JOIN FETCH o.customer c
        JOIN FETCH c.user
        JOIN FETCH o.classEntity ce
        JOIN FETCH ce.school s
        LEFT JOIN FETCH ce.books b
        WHERE c.customerId = :customerId
    """)
    List<Order> findOrdersWithDetailsByCustomerId(@Param("customerId") Long customerId);
}
