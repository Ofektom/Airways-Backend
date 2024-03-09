package org.ofektom.airwaysdemobackend.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.ofektom.airwaysdemobackend.dto.*;
import org.ofektom.airwaysdemobackend.enums.Role;
import org.ofektom.airwaysdemobackend.exception.InvalidTokenException;
import org.ofektom.airwaysdemobackend.exception.PasswordsDontMatchException;
import org.ofektom.airwaysdemobackend.model.PasswordResetToken;
import org.ofektom.airwaysdemobackend.model.User;
import org.ofektom.airwaysdemobackend.model.VerificationToken;
import org.ofektom.airwaysdemobackend.repository.PasswordResetTokenRepository;
import org.ofektom.airwaysdemobackend.repository.UserRepository;
import org.ofektom.airwaysdemobackend.repository.VerificationTokenRepository;
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

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final JwtUtils jwtUtils;
    private final EmailSenderService emailSenderService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordResetTokenRepository, VerificationTokenRepository verificationTokenRepository, JwtUtils jwtUtils, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.jwtUtils = jwtUtils;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not Found"));
    }

    @Override
    public User saveUser(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new RuntimeException("Email is already taken, try Logging In or Signup with another email" );
        }
        User user = new User();

        if (!signupDto.getPassword().equals (signupDto.getConfirmPassword())){
            throw new RuntimeException("Passwords are not the same");
        }
        if (!validatePassword(signupDto.getPassword())) {
            throw new RuntimeException("Password does not meet the required criteria");
        }

        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(signupDto.getConfirmPassword()));
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setPassportNumber(signupDto.getPhoneNumber());
        user.setEmail(signupDto.getEmail());
        user.setUserRole(Role.PASSENGER);
        return userRepository.save(user);
    }

    public boolean validatePassword(String password){
        String capitalLetterPattern = "(?=.*[A-Z])";
        String lowercaseLetterPattern = "(?=.*[a-z])";
        String digitPattern = "(?=.*\\d)";
        String symbolPattern = "(?=.*[@#$%^&+=])";
        String lengthPattern = ".{8,}";

        String regex = capitalLetterPattern + lowercaseLetterPattern + digitPattern + symbolPattern + lengthPattern;

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
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

    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);

    }


    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null){
            return "invalid";
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <=0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setIsEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }



    public User findUserByEmail(String username) {
        return userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Username Not Found" + username));
    }


    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken newlyCreatedPasswordResetToken = new PasswordResetToken(user, token);
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUserId(user.getId());
        if(passwordResetToken != null){
            passwordResetTokenRepository.delete(passwordResetToken);
        }
        passwordResetTokenRepository.save(newlyCreatedPasswordResetToken);
    }

    private String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return "invalid";
        }
        Calendar cal = Calendar.getInstance();
        if (passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }


    @Override
    public void forgotPassword(EmailSenderDto passwordDto, HttpServletRequest request) {
        User user = findUserByEmail(passwordDto.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + passwordDto.getEmail() + " not found");
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        emailSenderService.passwordResetTokenMail(user, emailSenderService.applicationUrl(request), token);
    }

    private Optional<User> getUserByPasswordReset(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    private void changePassword(User user, String newPassword, String newConfirmPassword) {

        if (newPassword.equals(newConfirmPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setConfirmPassword(passwordEncoder.encode(newConfirmPassword));
            userRepository.save(user);
        } else {
            throw new PasswordsDontMatchException("Passwords do not Match!");
        }
    }

    @Override
    public ResponseEntity<String> resetPassword(String token, ResetPasswordDto passwordDto) {
        String result = validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")) {
            throw new InvalidTokenException("Invalid Token");
        }
        Optional<User> user = getUserByPasswordReset(token);
        if (user.isPresent()) {
            changePassword(user.get(), passwordDto.getPassword(), passwordDto.getConfirmPassword());
            return new ResponseEntity<>("Password Reset Successful", HttpStatus.OK);
        } else {
            throw new InvalidTokenException("Invalid Token");
        }
    }


    @Override
    public ResponseEntity<String> updateProfile(Long userId, ProfileUpdateDto profileUpdateDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user with ID " + userId + " is not found"));


        if(profileUpdateDto.getFirstName() != null) {
            user.setFirstName(profileUpdateDto.getFirstName());
        }
        if(profileUpdateDto.getLastName() != null) {
            user.setLastName(profileUpdateDto.getLastName());
        }
        if(profileUpdateDto.getUsername() != null) {
            user.setEmail(profileUpdateDto.getUsername());
        }

        if(profileUpdateDto.getPhoneNumber() != null) {
            user.setPhoneNumber(profileUpdateDto.getPhoneNumber());
        }
        if(profileUpdateDto.getDateOfBirth() != null){
            user.setDateOfBirth(profileUpdateDto.getDateOfBirth());
        }
        if(profileUpdateDto.getGender() != null){
            user.setGender(profileUpdateDto.getGender());
        }
        if(profileUpdateDto.getState() != null){
            user.setState(profileUpdateDto.getState());
        }
        if(profileUpdateDto.getCountry() !=null) {
            user.setCountry(profileUpdateDto.getCountry());
        }
        if(profileUpdateDto.getPassportNumber() != null){
            user.setPassportNumber(profileUpdateDto.getPassportNumber());
        }
        if(profileUpdateDto.getMembershipNo() != null){
            user.setMembershipNo(profileUpdateDto.getMembershipNo());
        }

        userRepository.save(user);

        return new ResponseEntity<>("profile update successfully", HttpStatus.OK);
    }



}
