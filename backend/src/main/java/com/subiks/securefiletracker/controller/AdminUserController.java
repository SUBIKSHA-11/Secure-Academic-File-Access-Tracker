package com.subiks.securefiletracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.service.UserService;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")

public class AdminUserController {

    @Autowired
    private UserService userService;

    // ADMIN creates student / faculty
    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            @RequestParam Long departmentId,
            @RequestParam(required = false) Long semesterId) {

        return ResponseEntity.ok(
                userService.createUser(
                        name,
                        email,
                        password,
                        role,
                        departmentId,
                        semesterId
                )
        );
    }
}
