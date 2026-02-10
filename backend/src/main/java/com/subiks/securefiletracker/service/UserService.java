package com.subiks.securefiletracker.service;

import java.util.List;

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
            String name, String email, String password, String role, Long deptId, Long semId, String phone, String regNo) {

        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Semester sem = null;
        if (role.equals("STUDENT")) {
            sem = semesterRepository.findById(semId)
                    .orElseThrow(() -> new RuntimeException("Semester not found"));
        }
if (password == null || password.trim().isBlank()) {
    throw new RuntimeException("Password cannot be empty");
}

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(role);
        user.setDepartment(dept);
        user.setSemester(sem);
        user.setPhone(phone);

    if ("STUDENT".equals(role)) {
        user.setRegNo(regNo);
    }
        return userRepository.save(user);
    }
    

    // LOGIN SUPPORT
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public List<User> getUsersByRole(String role) {
    return userRepository.findByRole(role);
}

public List<User> getFacultyByDepartment(Long deptId) {
    return userRepository.findByDepartmentIdAndRole(deptId, "FACULTY");
}

public void deleteUser(Long id) {
    userRepository.deleteById(id);
}

public User updateUser(Long id, String name, String email, String phone) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    user.setName(name);
    user.setEmail(email);
    user.setPhone(phone);

    return userRepository.save(user);
}
public List<User> getStudentsByDeptAndYear(
        Long deptId,
        Long semId) {

    return userRepository
        .findByRoleAndDepartmentIdAndSemesterIdOrderByNameAsc(
            "STUDENT", deptId, semId
        );
}
public User updateStudent(
        Long id,
        String name,
        String email,
        String phone,
        String regNo) {

    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    user.setName(name);
    user.setEmail(email);
    user.setPhone(phone);
    user.setRegNo(regNo);

    return userRepository.save(user);
}

}
