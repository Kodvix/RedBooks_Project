package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.Publisher;
import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dto.RegisterPublisherRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public Publisher toDao(RegisterPublisherRequest request, PasswordEncoder encoder) {
        Publisher publisher = new Publisher();
        publisher.setName(request.getName());
        publisher.setEmail(request.getEmail());
        publisher.setPassword(encoder.encode(request.getPassword()));
        publisher.setRole(Role.PUBLISHER);
        return publisher;
    }
    public RegisterPublisherRequest toDto(Publisher publisher) {
        RegisterPublisherRequest dto = new RegisterPublisherRequest();

        dto.setName(publisher.getName());
        dto.setEmail(publisher.getEmail());
        dto.setPassword(publisher.getPassword());
        return dto;
    }
}
