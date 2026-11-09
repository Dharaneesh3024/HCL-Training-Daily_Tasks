package com.carrental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.carrental.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

            	    .requestMatchers("/api/auth/**").permitAll()

            	    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

            	    // Public car browsing
            	    .requestMatchers(
            	        org.springframework.http.HttpMethod.GET,
            	        "/api/cars/**"
            	    ).hasAnyRole("CUSTOMER", "ADMIN")

            	    // Admin-only car management
            	    .requestMatchers(
            	        org.springframework.http.HttpMethod.POST,
            	        "/api/cars"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        org.springframework.http.HttpMethod.DELETE,
            	        "/api/cars/**"
            	    ).hasRole("ADMIN")

            	    // Admin-only rental management
            	    .requestMatchers("/api/rentals").hasRole("ADMIN")

            	    .requestMatchers(
            	        org.springframework.http.HttpMethod.PUT,
            	        "/api/rentals/*/return"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        org.springframework.http.HttpMethod.GET,
            	        "/api/rentals/utilisation/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        org.springframework.http.HttpMethod.DELETE,
            	        "/api/rentals/**"
            	    ).hasRole("ADMIN")

            	    // Remaining rental operations require login for now
            	    .requestMatchers("/api/rentals/**").authenticated()

            	    .anyRequest().authenticated()
            	)

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}