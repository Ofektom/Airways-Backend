package org.ofektom.airwaysdemobackend.config;

import org.ofektom.airwaysdemobackend.enums.Role;
import org.ofektom.airwaysdemobackend.serviceImpl.UserServiceImpl;
import org.ofektom.airwaysdemobackend.utils.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    private UserServiceImpl userService;
    private JwtAuthenticationFilter authentication;


    @Autowired
    public WebSecurityConfig(@Lazy UserServiceImpl userService, JwtAuthenticationFilter authentication) {
        this.userService = userService;
        this.authentication = authentication;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public JavaMailSender javaMailSender() {
//        return new JavaMailSenderImpl();
//    }
//

    @Bean//bcryptPasswordEncoder is enabled for spring security hashing/salting of user's password information
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //AuthenticationProvider(DAOAuthenticationProvider) is enabled to function as the "bouncer" in our application. Checking
    //password and User information credibility.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(username -> userService.loadUserByUsername(username));
        return daoAuthenticationProvider;
    }


    @Bean//Creating our authorisation security for providing the right authorisation process
    // from before "logging in" till after "logging out"
    public SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequests ->
                        httpRequests.requestMatchers(
                                "/api/v1/flights/delete-flight/{Id}").hasAuthority(String.valueOf(Role.ADMIN))
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/api/v1/flights/availableFlight",
                                        "/api/v1/flights/fetch-all-flights").permitAll()
                                .requestMatchers(
                                        "/airports/**").authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authentication, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}


