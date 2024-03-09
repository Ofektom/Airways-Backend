package org.ofektom.airwaysdemobackend.controller;

import org.ofektom.airwaysdemobackend.dto.ProfileUpdateDto;
import org.ofektom.airwaysdemobackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileUpdateDto profileUpdateDto){
        return userService.updateProfile(userId, profileUpdateDto);
    }
}
