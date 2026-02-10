package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.service.UserService;

@RestController
@RequestMapping("/admin/users")
@CrossOrigin
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")


public class AdminUserController {

    @Autowired
    private UserService userService;

    //@Autowired
    //private UserRepository userRepository;
    // ADMIN creates student / faculty
    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            @RequestParam Long departmentId,
            @RequestParam(required = false) Long semesterId,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String regNo) {

        return ResponseEntity.ok(
                userService.createUser(
                        name,
                        email,
                        password,
                        role,
                        departmentId,
                        semesterId,phone,regNo
                )
        );
    }
    @GetMapping("/students")
public List<User> getStudents() {
    return userService.getUsersByRole("STUDENT");
}

@GetMapping("/faculty")
public List<User> getFaculty() {
    return userService.getUsersByRole("FACULTY");
}

// GET faculty by department
@GetMapping("/faculty/by-department/{deptId}")
public List<User> getFacultyByDepartment(
        @PathVariable Long deptId) {
    return userService.getFacultyByDepartment(deptId);
}

// DELETE faculty
@DeleteMapping("/{id}")
public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
}

// UPDATE faculty
@PutMapping("/{id}")
public User updateFaculty(
        @PathVariable Long id,
        @RequestParam String name,
        @RequestParam String email,
        @RequestParam String phone) {

    return userService.updateUser(id, name, email, phone);
}

}
