package org.ofektom.airwaysdemobackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.ofektom.airwaysdemobackend.dto.SignupDto;
import org.ofektom.airwaysdemobackend.enums.Role;
import org.ofektom.airwaysdemobackend.model.User;
import org.ofektom.airwaysdemobackend.repository.UserRepository;
import org.ofektom.airwaysdemobackend.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Function;


@Component
public class GoogleJwtUtils {


    @Value("${CLIENT.ID}")
    private String CLIENT_ID;
    private UserRepository userRepository;
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private JwtUtils utils;
    @Autowired
    public GoogleJwtUtils(UserRepository userRepository, @Lazy UserServiceImpl userService, PasswordEncoder passwordEncoder, JwtUtils utils) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.utils = utils;
    }




    private final Function<String, SignupDto> getUserFromIdToken = (token)-> {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();


        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();


        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);
            String email = payload.getEmail();
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");
            return SignupDto
                    .builder()
                    .firstName(givenName)
                    .lastName(familyName)
                    .email(email)
                    .phoneNumber("00000000000")
                    .password("Airways24")
                    .confirmPassword("Airways24")
                    .build();


        }
        return null;
    };


    public Function<SignupDto, String> saveOauthUser = userDto -> {
        if (userRepository.existsByEmail(userDto.getEmail())){
            UserDetails userDetails = userService.loadUserByUsername(userDto.getEmail());
            return utils.createJwt.apply(userDetails);
        }
        else{
            User user = new ObjectMapper().convertValue(userDto, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole(Role.PASSENGER);
            user.setDateOfBirth(LocalDate.now().toString());
            user.setGender("NA");
            user.setMembershipNo("NA");
            user.setPassportNumber("NA");
            user.setState("NA");
            user.setCountry("NA");
            user = userRepository.save(user);
            return utils.createJwt.apply(userService.loadUserByUsername(user.getUsername()));
        }
    };


    public String googleOauthUserJWT(String token){
        SignupDto user =  getUserFromIdToken.apply(token);
        return saveOauthUser.apply(user);
    }
}
