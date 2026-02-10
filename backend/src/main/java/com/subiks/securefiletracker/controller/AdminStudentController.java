package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.service.UserService;
@RestController
@RequestMapping("/admin/students")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@CrossOrigin
public class AdminStudentController {

    @Autowired
    private UserService userService;

    @GetMapping("/{deptId}/{semId}")
    public List<User> getStudents(
            @PathVariable Long deptId,
            @PathVariable Long semId) {

        return userService
            .getStudentsByDeptAndYear(deptId, semId);
    }

    @PutMapping("/{id}")
    public User updateStudent(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String regNo) {

        return userService
            .updateStudent(id, name, email, phone, regNo);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

