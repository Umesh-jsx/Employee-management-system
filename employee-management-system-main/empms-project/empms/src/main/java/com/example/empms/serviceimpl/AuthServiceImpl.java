package com.example.empms.serviceimpl;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.empms.dao.RoleRepository;
import com.example.empms.dao.UserRepository;
import com.example.empms.entity.Role;
import com.example.empms.entity.User;
import com.example.empms.request.LoginRequest;
import com.example.empms.request.RegisterRequest;
import com.example.empms.service.AuthService;
import com.example.empms.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;


	@Override
	public String register(RegisterRequest request) {
		Role role = roleRepository.findByName(request.getRoleName().toUpperCase())
				.orElseThrow(() -> new RuntimeException("Role not found"));
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(role);
		
		userRepository.save(user);
		return "User registered successfully";
	}

	@Override
	public String login(LoginRequest request) {

		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid password"); 
		}
		
		return jwtUtil.generateToken(user.getUsername());		
	}

}
