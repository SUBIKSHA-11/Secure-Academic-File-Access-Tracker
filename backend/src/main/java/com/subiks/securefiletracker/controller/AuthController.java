package com.subiks.securefiletracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;
@Autowired
private JwtUtil jwtUtil;

    // REGISTER API
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.saveUser(user);
    }
  @PostMapping("/login")
public String login(@RequestBody User loginUser) {

    User dbUser = userService.findByEmail(loginUser.getEmail());

    if (dbUser == null) {
        return "User not found";
    }

    boolean isPasswordCorrect = userService.checkPassword(
            loginUser.getPassword(),
            dbUser.getPassword()
    );

    if (!isPasswordCorrect) {
        return "Invalid password";
    }

    // JWT generate pannrom
    String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getRole());

    return token;
}

}



