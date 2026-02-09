package com.subiks.securefiletracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.model.Department;
import com.subiks.securefiletracker.model.Semester;
import com.subiks.securefiletracker.model.Subject;
import com.subiks.securefiletracker.repository.DepartmentRepository;
import com.subiks.securefiletracker.repository.SemesterRepository;
import com.subiks.securefiletracker.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    public Subject addSubject(Long deptId, Long semId, String name) {

        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Semester sem = semesterRepository.findById(semId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        Subject subject = new Subject();
        subject.setName(name);
        subject.setDepartment(dept);
        subject.setSemester(sem);

        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjects(Long deptId, Long semId) {
        return subjectRepository
                .findByDepartmentIdAndSemesterId(deptId, semId);
    }
    

public void addSubject(String name, Long semesterId) {

    Semester semester = semesterRepository.findById(semesterId)
            .orElseThrow(() -> new RuntimeException("Semester not found"));

    Subject subject = new Subject();
    subject.setName(name);
    subject.setSemester(semester);

    subjectRepository.save(subject);
}

}
