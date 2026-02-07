package com.subiks.securefiletracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subiks.securefiletracker.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByDepartmentIdAndSemesterId(
            Long departmentId,
            Long semesterId
    );
}
