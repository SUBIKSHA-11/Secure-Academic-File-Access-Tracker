package com.subiks.securefiletracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subiks.securefiletracker.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
