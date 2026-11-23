
package com.carrental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

                // Car management
                .requestMatchers(HttpMethod.GET, "/api/cars/**")
                    .hasAnyRole("CUSTOMER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/cars")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/api/cars/**")
                    .hasRole("ADMIN")

                // Rental management
                .requestMatchers(HttpMethod.GET, "/api/rentals")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/rentals")
                    .authenticated()

                .requestMatchers(HttpMethod.PUT, "/api/rentals/*/return")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/rentals/utilisation/**")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/api/rentals/**")
                    .hasRole("ADMIN")

                .requestMatchers("/api/rentals/**")
                    .authenticated()

                // Customer profile - authenticated users
                .requestMatchers(HttpMethod.GET, "/api/customers/me")
                    .authenticated()

                .requestMatchers(HttpMethod.PUT, "/api/customers/me")
                    .authenticated()

                // Admin-only customer management
                .requestMatchers(HttpMethod.POST, "/api/customers")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/customers")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/customers/*")
                    .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/api/customers/**")
                    .hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
