package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dto.UserProfileDto;
import com.org.kodvix.redbooks.dto.UserRegisterDto;
import com.org.kodvix.redbooks.dto.UserLoginDto;

public interface UserService {
    String registerUser(UserRegisterDto dto);
    String loginUser(UserLoginDto dto);
    UserProfileDto getUserProfile(String email);
    UserProfileDto updateUserProfile(String email, UserProfileDto dto);
}