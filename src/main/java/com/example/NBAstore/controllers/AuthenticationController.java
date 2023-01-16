package com.example.NBAstore.controllers;

import com.example.NBAstore.config.JwtUtil;
import com.example.NBAstore.enums.Role;
import com.example.NBAstore.exceptions.UserAlreadyExistsException;
import com.example.NBAstore.models.LoginCredentials;
import com.example.NBAstore.models.User;
import com.example.NBAstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(
            UserRepository userRepository,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user) {
        Optional<User> requestedUser = userRepository.findByEmail(user.getEmail());

        if (requestedUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        if (Objects.equals(user.getEmail(), "jesseouwehand@gmail.com")) {
            user.setRole(Role.admin);
        } else {
            user.setRole(Role.user);
        }
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                    body.getEmail(),
                    body.getPassword()
            );

            authenticationManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());
            return Collections.singletonMap("token", token);
        } catch (AuthenticationException authenticationException) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }
}
