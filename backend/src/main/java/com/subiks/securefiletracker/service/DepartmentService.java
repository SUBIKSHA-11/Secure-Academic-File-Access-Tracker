package com.subiks.securefiletracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.model.Department;
import com.subiks.securefiletracker.repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department addDepartment(Department dept) {
        return departmentRepository.save(dept);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
