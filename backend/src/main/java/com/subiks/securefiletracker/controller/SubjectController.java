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

import com.subiks.securefiletracker.model.Subject;
import com.subiks.securefiletracker.service.SubjectService;

@RestController
@RequestMapping("/subjects")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    // ADMIN adds subject
    @PostMapping
    public ResponseEntity<?> addSubject(
            @RequestParam Long departmentId,
            @RequestParam Long semesterId,
            @RequestParam String name) {

        return ResponseEntity.ok(
                subjectService.addSubject(departmentId, semesterId, name)
        );
    }

    // STUDENT/FACULTY fetch subjects
    
    @PreAuthorize("hasAnyAuthority('ROLE_STUDENT','ROLE_FACULTY')")
    @GetMapping("/{deptId}/{semId}")
    public ResponseEntity<List<Subject>> getSubjects(
            @PathVariable Long deptId,
            @PathVariable Long semId) {

        return ResponseEntity.ok(
                subjectService.getSubjects(deptId, semId)
        );
    }
    @PreAuthorize("hasAuthority('ROLE_FACULTY')")
@PostMapping("/add")
public ResponseEntity<?> addSubject(
        @RequestParam String name,
        @RequestParam Long semesterId) {

    subjectService.addSubject(name, semesterId);
    return ResponseEntity.ok("Subject added");
}

}
