
package com.carrental.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carrental.model.User;
import com.carrental.repository.UserRepository;
import com.carrental.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
        
    }

    public User register(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("CUSTOMER");
        }

        return userRepository.save(user);
    }
    
    public String login(String email,String password) {
    	User user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
    	if(!passwordEncoder.matches(password, user.getPassword())) {
    			throw new IllegalArgumentException("Invalid email or password");
    	}
    	return jwtService.generateToken(user.getEmail());
    }
    
}
