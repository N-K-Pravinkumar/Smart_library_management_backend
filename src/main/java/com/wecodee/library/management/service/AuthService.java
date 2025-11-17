package com.wecodee.library.management.service;

import com.wecodee.library.management.config.JwtService;
import com.wecodee.library.management.model.User;
import com.wecodee.library.management.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(User user) {
        // Check for duplicate email
        if (authRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already registered!";
        }

        // Hardcode librarian as ADMIN
        if (user.getEmail().equalsIgnoreCase("admin123@gmail.com")) {
            user.setRole("ADMIN");
        } else {
            user.setRole("STUDENT");
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user to DB
        authRepository.save(user);

        return "User registered successfully!";
    }

    public String login(String email, String password) {
        // Hardcoded admin credentials
        if (email.equals("admin123@gmail.com") && password.equals("123123")) {
            return jwtService.generateToken(email, "ADMIN");
        }

        // Check if user exists
        User user = authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtService.generateToken(email, user.getRole());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
