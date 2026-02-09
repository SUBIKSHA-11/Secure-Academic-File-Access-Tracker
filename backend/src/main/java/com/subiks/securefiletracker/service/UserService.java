package com.subiks.securefiletracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.model.Department;
import com.subiks.securefiletracker.model.Semester;
import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.repository.DepartmentRepository;
import com.subiks.securefiletracker.repository.SemesterRepository;
import com.subiks.securefiletracker.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

    // ADMIN creates student / faculty
    public User createUser(
            String name,
            String email,
            String password,
            String role,
            Long deptId,
            Long semId) {

        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Semester sem = null;
        if (role.equals("STUDENT")) {
            sem = semesterRepository.findById(semId)
                    .orElseThrow(() -> new RuntimeException("Semester not found"));
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        user.setDepartment(dept);
        user.setSemester(sem);

        return userRepository.save(user);
    }

    // LOGIN SUPPORT
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
