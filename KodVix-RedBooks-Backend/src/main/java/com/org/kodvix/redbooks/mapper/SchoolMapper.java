package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dao.School;
import com.org.kodvix.redbooks.dto.RegisterSchoolRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {

    public School toDao(RegisterSchoolRequest request, PasswordEncoder encoder) {
        School school = new School();
        school.setName(request.getName());
        school.setEmail(request.getEmail());
        school.setPassword(encoder.encode(request.getPassword()));
        school.setRole(Role.SCHOOL);
        return school;
    }
    public RegisterSchoolRequest toDto(School school) {
        RegisterSchoolRequest dto = new RegisterSchoolRequest();
        dto.setName(school.getName());
        dto.setEmail(school.getEmail());
        dto.setPassword(school.getPassword());
        return dto;
    }
}
