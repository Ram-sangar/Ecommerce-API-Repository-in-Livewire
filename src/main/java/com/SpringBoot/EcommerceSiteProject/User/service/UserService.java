package com.SpringBoot.EcommerceSiteProject.User.service;

import com.SpringBoot.EcommerceSiteProject.Configuration.JwtUtils;
import com.SpringBoot.EcommerceSiteProject.User.model.LoginDTO;
import com.SpringBoot.EcommerceSiteProject.User.model.User;
import com.SpringBoot.EcommerceSiteProject.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public String register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Added Successfully";
    }


    public ResponseEntity<?> authenticate(LoginDTO login){
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (BadCredentialsException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            Optional<User> userDetails = userRepository.findByEmail(login.getEmail());
            if (userDetails.isPresent()) {
                String token = jwtUtils.generateToken(userDetails.get());
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.noContent().build();
            }
        }
    }

