package com.org.kodvix.redbooks.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/register", "/api/user/login").permitAll()
                        .requestMatchers("/api/school/**").hasRole("SCHOOL_ADMIN")
                        // Allow GET access to all 3 roles
                        .requestMatchers(HttpMethod.GET, "/api/publishers/books", "/api/publishers/books/**")
                        .hasAnyRole("USER", "SCHOOL_ADMIN", "PUBLISHER_ADMIN")
                        // All other methods (POST/PUT/DELETE) on books only for PUBLISHER_ADMIN
                        .requestMatchers("/api/publishers/books", "/api/publishers/books/**")
                        .hasRole("PUBLISHER_ADMIN")
                       // .requestMatchers("/api/publishers/books", "/api/publishers/books/**").hasRole("PUBLISHER_ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("USER","SCHOOL_ADMIN", "PUBLISHER_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults()); // Replace with JWT configuration if applicable

        return http.build();

    }
}
