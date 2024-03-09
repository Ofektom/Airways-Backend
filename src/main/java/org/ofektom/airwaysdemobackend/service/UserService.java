package org.ofektom.airwaysdemobackend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.ofektom.airwaysdemobackend.dto.*;
import org.ofektom.airwaysdemobackend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {
    User saveUser(SignupDto signupDto);
    ResponseEntity<String> loginUser(LoginDto loginDto);

    String logoutUser(Authentication authentication, HttpServletRequest request);
    void createPasswordResetTokenForUser(User user, String token);

    void forgotPassword(EmailSenderDto passwordDto, HttpServletRequest request);

    ResponseEntity<String> resetPassword(String token, ResetPasswordDto passwordDto);
    ResponseEntity<String> updateProfile(Long UserId, ProfileUpdateDto profileUpdateDto);
}
