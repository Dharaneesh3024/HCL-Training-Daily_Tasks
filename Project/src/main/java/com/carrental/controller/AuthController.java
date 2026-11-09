package com.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.dto.RegisterRequest;
import com.carrental.model.User;
import com.carrental.service.AuthService;
import com.carrental.dto.LoginRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	private final AuthService authService;
	public AuthController(AuthService authService) {
		this.authService=authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request ){
		User user=new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setRole("CUSTOMER");
		authService.register(user);
		return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Customer registered successfully");
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
		String token=authService.login(request.getEmail(), request.getPassword());
		return ResponseEntity.ok(java.util.Map.of(
				"message","Login Successful",
				"token",token));
	}
	
}
