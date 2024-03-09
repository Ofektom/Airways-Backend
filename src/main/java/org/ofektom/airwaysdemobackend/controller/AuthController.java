package org.ofektom.airwaysdemobackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ofektom.airwaysdemobackend.dto.EmailSenderDto;
import org.ofektom.airwaysdemobackend.dto.LoginDto;
import org.ofektom.airwaysdemobackend.dto.ResetPasswordDto;
import org.ofektom.airwaysdemobackend.dto.SignupDto;
import org.ofektom.airwaysdemobackend.event.RegistrationCompleteEvent;
import org.ofektom.airwaysdemobackend.exception.InvalidTokenException;
import org.ofektom.airwaysdemobackend.exception.MailConnectionException;
import org.ofektom.airwaysdemobackend.model.User;
import org.ofektom.airwaysdemobackend.model.VerificationToken;
import org.ofektom.airwaysdemobackend.serviceImpl.EmailSenderService;
import org.ofektom.airwaysdemobackend.serviceImpl.UserServiceImpl;
import org.ofektom.airwaysdemobackend.utils.GoogleJwtUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final UserServiceImpl userService;
    private final GoogleJwtUtils googleJwtUtils;
    private final ApplicationEventPublisher publisher;

    private final EmailSenderService emailSenderService;

    public AuthController(UserServiceImpl userService, GoogleJwtUtils googleJwtUtils, ApplicationEventPublisher publisher, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.googleJwtUtils = googleJwtUtils;
        this.publisher = publisher;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/google/{tkn}")
    public ResponseEntity<String> authorizeOauthUser(@PathVariable("tkn") String token){
        return ResponseEntity.ok(googleJwtUtils.googleOauthUserJWT(token));
    }


    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody SignupDto signupDto, final HttpServletRequest request){
        User user = userService.saveUser(signupDto);
        publisher.publishEvent(new RegistrationCompleteEvent(user, emailSenderService.applicationUrl(request)));
        return new ResponseEntity<>("Signup successful, go to your mail to verify your account", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        return userService.loginUser(loginDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication, HttpServletRequest request) {
        String result = userService.logoutUser(authentication, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/verifyRegistration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid token")){
            return new ResponseEntity<>( "User Verified Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("User signed up", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailSenderDto passwordDto, HttpServletRequest request){
        userService.forgotPassword(passwordDto, request);
        return new ResponseEntity<>("Forgot password email successfully sent", HttpStatus.OK);

    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPasswordDto passwordDto) {
        return userService.resetPassword(token, passwordDto);
    }

}
