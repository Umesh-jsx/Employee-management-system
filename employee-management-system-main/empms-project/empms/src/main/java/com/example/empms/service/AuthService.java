package com.example.empms.service;

import com.example.empms.request.LoginRequest;
import com.example.empms.request.RegisterRequest;

public interface AuthService {

	String register(RegisterRequest request);

	String login(LoginRequest request);

}
