package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.Department;
import com.subiks.securefiletracker.service.DepartmentService;

@RestController
@RequestMapping("/admin/departments")
@CrossOrigin
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}

