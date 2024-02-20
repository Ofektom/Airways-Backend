package org.ofektom.airwaysdemobackend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.ofektom.airwaysdemobackend.dto.LoginDto;
import org.ofektom.airwaysdemobackend.dto.SignupDto;
import org.ofektom.airwaysdemobackend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {
    User saveUser(SignupDto signupDto);
    ResponseEntity<String> loginUser(LoginDto loginDto);

    String logoutUser(Authentication authentication, HttpServletRequest request);
}
