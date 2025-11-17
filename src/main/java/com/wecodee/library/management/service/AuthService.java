package com.wecodee.library.management.service;

import com.wecodee.library.management.config.JwtService;
import com.wecodee.library.management.dto.LoginRequest;
import com.wecodee.library.management.dto.LoginResponse;
import com.wecodee.library.management.dto.RegisterRequest;
import com.wecodee.library.management.model.User;
import com.wecodee.library.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        User user = new User();
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // set default role
        if (request.getRole() == null || request.getRole().isEmpty()) {
            user.setRole("Student");
        } else {
            user.setRole(request.getRole());
        }

        userRepository.save(user);
        return "User registered successfully!";
    }

    // ✅ Updated Login method
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setId(user.getUserId());
        response.setUsername(user.getUserName());
        response.setRole(user.getRole());

        return response;
    }
}


//package com.wecodee.library.management.service;
//
//import com.wecodee.library.management.config.JwtService;
//import com.wecodee.library.management.dto.RegisterRequest;
//import com.wecodee.library.management.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//import com.wecodee.library.management.dto.LoginRequest;
//import com.wecodee.library.management.repository.UserRepository;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public String register(RegisterRequest request) {
//        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//            throw new RuntimeException("User already exists!");
//        }
//
//        User user = new User();
//        user.setUserName(request.getUsername());
//        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        userRepository.save(user);
//
//        return "User registered successfully!";
//    }
//
//    public String login(LoginRequest request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            return jwtService.generateToken(user.getEmail());
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//}
//
//
//
