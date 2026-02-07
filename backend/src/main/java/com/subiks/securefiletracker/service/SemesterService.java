package com.subiks.securefiletracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.model.Department;
import com.subiks.securefiletracker.model.Semester;
import com.subiks.securefiletracker.repository.DepartmentRepository;
import com.subiks.securefiletracker.repository.SemesterRepository;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Semester addSemester(Long deptId, int number) {

        Department dept =
                departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Semester sem = new Semester();
        sem.setNumber(number);
        sem.setDepartment(dept);

        return semesterRepository.save(sem);
    }

    public List<Semester> getSemestersByDepartment(Long deptId) {
        return semesterRepository.findByDepartmentId(deptId);
    }
}
