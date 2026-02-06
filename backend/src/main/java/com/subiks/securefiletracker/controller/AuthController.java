package com.subiks.securefiletracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.service.UserService;
import com.subiks.securefiletracker.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
@Autowired
private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    // LOGIN
   @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {

    User loggedUser =
            userService.login(user.getEmail(), user.getPassword());

    if (loggedUser == null) {
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    String token = jwtUtil.generateToken(loggedUser.getEmail(),loggedUser.getRole());

    return ResponseEntity.ok(token);
}

}
