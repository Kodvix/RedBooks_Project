package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.exception.*;
import com.org.kodvix.redbooks.mapper.*;
import com.org.kodvix.redbooks.repository.CustomerRepository;
import com.org.kodvix.redbooks.repository.UserRepository;
import com.org.kodvix.redbooks.security.JwtUtils;
import com.org.kodvix.redbooks.service.AuthenticationService;
import com.org.kodvix.redbooks.service.MailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final CustomerMapper customerMapper;
    private final SchoolMapper schoolMapper;
    private final PublisherMapper publisherMapper;
    private final MailService mailService;


    private final Map<String, String> otpMap = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public void registerCustomer(RegisterCustomerRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email is already in use");
        }
        User school = userRepository.findAll().stream()
                .filter(u -> u.getRole().name().equals("SCHOOL"))
                .filter(u -> u.getName().equalsIgnoreCase(req.getSchoolName()))
                .findFirst()
                .orElseThrow(() ->
                        new SchoolNotFoundException("School '" + req.getSchoolName() + "' is not registered in the system.")
                );




        User user = customerMapper.toUser(req, passwordEncoder);
        userRepository.save(user);

        Customer customer = customerMapper.toCustomer(req, user);
        customerRepository.save(customer);
    }


    @Override
    @Transactional
    public void registerSchool(RegisterSchoolRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email is already in use");
        }

        School school = schoolMapper.toDao(req, passwordEncoder);
        userRepository.save(school);
    }

    @Override
    @Transactional
    public void registerPublisher(RegisterPublisherRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email is already in use");
        }

        Publisher publisher = publisherMapper.toDao(req, passwordEncoder);
        userRepository.save(publisher);
    }

    @Override
    public AuthResponse authenticate(AuthRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new AuthResponse(jwtUtils.generateJwtToken(user.getEmail()));
    }

    @Override
    @Transactional
    public AuthResponse changeEmail(ChangeEmailRequest req, String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (userRepository.findByEmail(req.getNewEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email is already in use");
        }

        user.setEmail(req.getNewEmail());
        userRepository.save(user);

        return new AuthResponse(jwtUtils.generateJwtToken(user.getEmail()));
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest req, String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpMap.put(req.getEmail(), otp);


        mailService.sendEmail(
                req.getEmail(),
                "RedBooks - OTP for Password Reset",
                "Your OTP is: " + otp + "\nUse this to reset your password."
        );
    }

    @Override
    @Transactional
    public void verifyOtpAndResetPassword(VerifyOtpRequest req) {
       String storedOtp = otpMap.get(req.getEmail());
        if (storedOtp == null || !storedOtp.equals(req.getOtp())) {
            throw new OtpValidationException("Invalid or expired OTP");
        }

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        otpMap.remove(req.getEmail());
    }
}
