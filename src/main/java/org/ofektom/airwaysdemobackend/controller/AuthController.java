package org.ofektom.airwaysdemobackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.ofektom.airwaysdemobackend.dto.LoginDto;
import org.ofektom.airwaysdemobackend.dto.SignupDto;
import org.ofektom.airwaysdemobackend.model.User;
import org.ofektom.airwaysdemobackend.serviceImpl.UserServiceImpl;
import org.ofektom.airwaysdemobackend.utils.GoogleJwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserServiceImpl userService;
    private final GoogleJwtUtils googleJwtUtils;

    public AuthController(UserServiceImpl userService, GoogleJwtUtils googleJwtUtils) {
        this.userService = userService;
        this.googleJwtUtils = googleJwtUtils;
    }

    @GetMapping("/google/{tkn}")
    public ResponseEntity<String> authorizeOauthUser(@PathVariable("tkn") String token){
        return ResponseEntity.ok(googleJwtUtils.googleOauthUserJWT(token));
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@Valid @RequestBody SignupDto signupDto) throws RuntimeException {
        User user = userService.saveUser(signupDto);

        if(user != null) {
            return new ResponseEntity<>("Registration Successful", HttpStatus.OK);
        }else{
            throw new RuntimeException("Registration Unsuccessful");
        }

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
}
