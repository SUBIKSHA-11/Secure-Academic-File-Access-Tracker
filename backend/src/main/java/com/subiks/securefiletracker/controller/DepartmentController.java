package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.Department;
import com.subiks.securefiletracker.service.DepartmentService;

@RestController
@RequestMapping("/admin/departments")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // ADMIN adds department
    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody Department dept) {
        return ResponseEntity.ok(departmentService.addDepartment(dept));
    }

    // Everyone can view departments
    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }
}
