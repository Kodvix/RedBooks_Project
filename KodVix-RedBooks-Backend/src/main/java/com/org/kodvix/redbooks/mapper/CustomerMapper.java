package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.Customer;
import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dao.User;
import com.org.kodvix.redbooks.dto.RegisterCustomerRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public User toUser(RegisterCustomerRequest request, PasswordEncoder encoder) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        return user;
    }

    public Customer toCustomer(RegisterCustomerRequest request, User user) {
        return Customer.builder()
                .user(user)
                .schoolName(request.getSchoolName())
                .studentClass(request.getStudentClass())
                .address(request.getAddress())
                .build();
    }
}
