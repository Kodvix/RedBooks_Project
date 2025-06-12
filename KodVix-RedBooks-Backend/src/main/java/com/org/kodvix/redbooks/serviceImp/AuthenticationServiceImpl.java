package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.service.AuthenticationService;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.repository.UserRepository;
import com.org.kodvix.redbooks.security.JwtUtils;
import com.org.kodvix.redbooks.mapper.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    private final CustomerMapper customerMapper;
    private final SchoolMapper schoolMapper;
    private final PublisherMapper publisherMapper;

    @Override
    @Transactional
    public AuthResponse registerCustomer(RegisterCustomerRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        Customer customer = customerMapper.toDao(req, passwordEncoder);
        userRepository.save(customer);

        return new AuthResponse(jwtUtils.generateJwtToken(customer.getEmail()));
    }

    @Override
    @Transactional
    public AuthResponse registerSchool(RegisterSchoolRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        School school = schoolMapper.toDao(req, passwordEncoder);
        userRepository.save(school);

        return new AuthResponse(jwtUtils.generateJwtToken(school.getEmail()));
    }

    @Override
    @Transactional
    public AuthResponse registerPublisher(RegisterPublisherRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use");
        }

        Publisher publisher = publisherMapper.toDao(req, passwordEncoder);
        userRepository.save(publisher);

        return new AuthResponse(jwtUtils.generateJwtToken(publisher.getEmail()));
    }

    @Override
    public AuthResponse authenticate(AuthRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(jwtUtils.generateJwtToken(user.getEmail()));
    }
}
