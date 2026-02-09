package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.Semester;
import com.subiks.securefiletracker.service.SemesterService;

@RestController
@RequestMapping("/semesters")
@CrossOrigin
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    // ADMIN adds semester for a department
    @PostMapping("/{deptId}")
    public ResponseEntity<?> addSemester(
            @PathVariable Long deptId,
            @RequestParam int number) {

        return ResponseEntity.ok(
                semesterService.addSemester(deptId, number)
        );
    }

    // Fetch semesters by department
    
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_FACULTY','ROLE_ADMIN')")
    @GetMapping("/{deptId}")
    public ResponseEntity<List<Semester>> getSemesters(
            @PathVariable Long deptId) {

        return ResponseEntity.ok(
                semesterService.getSemestersByDepartment(deptId)
        );
    }
}
