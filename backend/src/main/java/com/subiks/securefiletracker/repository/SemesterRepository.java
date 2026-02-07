package com.subiks.securefiletracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subiks.securefiletracker.model.Semester;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

    List<Semester> findByDepartmentId(Long departmentId);
}
