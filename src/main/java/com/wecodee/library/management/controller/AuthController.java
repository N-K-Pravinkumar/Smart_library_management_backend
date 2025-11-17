package com.wecodee.library.management.controller;

import com.wecodee.library.management.model.User;
import com.wecodee.library.management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user){
       return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return authService.login(user.getEmail(),user.getPassword());
    }

}
