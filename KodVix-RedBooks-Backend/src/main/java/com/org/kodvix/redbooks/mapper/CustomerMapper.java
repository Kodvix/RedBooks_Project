package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.Customer;
import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dto.RegisterCustomerRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toDao(RegisterCustomerRequest request, PasswordEncoder encoder) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(encoder.encode(request.getPassword()));
        customer.setRole(Role.CUSTOMER);
        customer.setSchoolName(request.getSchoolName());
        customer.setStudentClass(request.getStudentClass());
        return customer;
    }
}