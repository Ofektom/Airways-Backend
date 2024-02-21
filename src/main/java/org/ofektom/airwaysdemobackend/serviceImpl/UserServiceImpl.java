package org.ofektom.airwaysdemobackend.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.ofektom.airwaysdemobackend.dto.LoginDto;
import org.ofektom.airwaysdemobackend.dto.SignupDto;
import org.ofektom.airwaysdemobackend.enums.Role;
import org.ofektom.airwaysdemobackend.model.User;
import org.ofektom.airwaysdemobackend.repository.UserRepository;
import org.ofektom.airwaysdemobackend.service.UserService;
import org.ofektom.airwaysdemobackend.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not Found"));
    }

    public User saveUser(SignupDto signupDto) {
        User user = new ObjectMapper().convertValue(signupDto, User.class);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already taken, try Logging In");
        }

        if (user.isPasswordMatching()) {
            user.setUserRole(Role.PASSENGER);
            user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
            user.setConfirmPassword(passwordEncoder.encode(signupDto.getConfirmPassword()));

            return userRepository.save(user);
        } else {
            throw new RuntimeException("Passwords do not Match!");
        }
    }

    @Override
    public ResponseEntity<String> loginUser(LoginDto loginDto) {
        UserDetails user = loadUserByUsername(loginDto.getEmail());
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String token = jwtUtils.createJwt.apply(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Email or Password not correct!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public String logoutUser(Authentication authentication, HttpServletRequest request) {
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();
            return "User logged Out Successfully";
        } else {
            return "User not authenticated";
        }
    }

}
