package com.subiks.securefiletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // REGISTER
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // LOGIN
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) return null;

        if (encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    // USED BY JWT FILTER
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }
}
