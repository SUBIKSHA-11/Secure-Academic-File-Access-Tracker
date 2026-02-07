package com.subiks.securefiletracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.service.UserService;
import com.subiks.securefiletracker.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // 🔐 LOGIN ONLY (NO REGISTER)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {

        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity.status(401)
                    .body("Invalid credentials");
        }

        // password check
        boolean match = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                .matches(request.getPassword(), user.getPassword());

        if (!match) {
            return ResponseEntity.status(401)
                    .body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(token);
    }
}
