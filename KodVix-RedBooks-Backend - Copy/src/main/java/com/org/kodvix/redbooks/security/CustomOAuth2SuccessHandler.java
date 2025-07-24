package com.org.kodvix.redbooks.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.kodvix.redbooks.dao.Role;
import com.org.kodvix.redbooks.dao.User;
import com.org.kodvix.redbooks.dto.AuthResponse;
import com.org.kodvix.redbooks.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();
        String email = oAuthUser.getAttribute("email");

        if (email == null) {
            sendJson(response, 400, new AuthResponse("Email not found in Google profile"));
            return;
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            sendJson(response, 404, new AuthResponse("User with email " + email + " not registered"));
            return;
        }

        if (user.getRole() != Role.CUSTOMER) {
            sendJson(response, 403, new AuthResponse("Only customers can login via Google"));
            return;
        }

        String token = jwtUtils.generateJwtToken(email);
        sendJson(response, 200, new AuthResponse(token));
    }

    private void sendJson(HttpServletResponse response, int status, AuthResponse body) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), body);
    }
}
