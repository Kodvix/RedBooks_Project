//package com.org.kodvix.redbooks.serviceImp;
//
//public class UserServiceImpl {
//}


package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dao.SchoolDao;
import com.org.kodvix.redbooks.dao.UserDao;
import com.org.kodvix.redbooks.dto.PublisherDto;
import com.org.kodvix.redbooks.dto.UserLoginDto;
import com.org.kodvix.redbooks.dto.UserProfileDto;
import com.org.kodvix.redbooks.dto.UserRegisterDto;
import com.org.kodvix.redbooks.exception.UserException;
import com.org.kodvix.redbooks.repository.UserRepository;
import com.org.kodvix.redbooks.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public String registerUser(UserRegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserException("Email already exists");
        }
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new UserException("Phone number already exists");
        }

        UserDao user = convertToDao(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRegisteredAt(LocalDateTime.now());

        userRepository.save(user);
        return "User registered successfully";
    }
    @Override
    public String loginUser(UserLoginDto dto) {
        UserDao user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserException("Invalid email or password");
        }
        Role role = user.getRole();
        return "Login with role " + role.name() + " is successful";

    }

    @Override
    public UserProfileDto getUserProfile(String email) {
        UserDao user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        return convertToProfileDto(user);
    }

    public UserDao getLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    @Override
    public UserProfileDto updateUserProfile(String email, UserProfileDto dto) {
        UserDao user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));

        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        if (user.getRole() == Role.PUBLISHER_ADMIN && dto.getPublisher() != null) {
            if (user.getPublisher() == null) {
                // If no publisher linked yet, create a new one or throw error based on your logic
                // For example:
                // user.setPublisher(new PublisherDao());
                // Or throw exception if publisher must exist
            }

            // Update publisher fields explicitly
            user.getPublisher().setPublisherName(dto.getPublisher().getPublisherName());
            user.getPublisher().setEmail(dto.getPublisher().getEmail());
            user.getPublisher().setPhoneNumber(dto.getPublisher().getPhoneNumber());
            user.getPublisher().setAddress(dto.getPublisher().getAddress());
        }

        userRepository.save(user);

        return convertToProfileDto(user);
    }

    // Mapper methods
    private UserDao convertToDao(UserRegisterDto dto) {
        UserDao user = modelMapper.map(dto, UserDao.class);

        return user;
        }

        private UserProfileDto convertToProfileDto(UserDao user) {
            UserProfileDto dto = new UserProfileDto();
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setRole(user.getRole().toString());
            if (user.getRole() == Role.PUBLISHER_ADMIN && user.getPublisher() != null) {
                dto.setPublisher(modelMapper.map(user.getPublisher(), PublisherDto.class));
            }
            return dto;
        }
    }
