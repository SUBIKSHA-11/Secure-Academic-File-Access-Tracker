package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.Lesson;
import com.subiks.securefiletracker.service.LessonService;

@RestController
@RequestMapping("/lessons")
@CrossOrigin
public class AdminLessonController {

    @Autowired
    private LessonService lessonService;

    // 🔥 THIS FIXES YOUR 500
    @PreAuthorize("hasAnyAuthority('ROLE_FACULTY','ROLE_ADMIN','ROLE_STUDENT')")
    @GetMapping("/{subjectId}")
    public ResponseEntity<List<Lesson>> getLessons(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                lessonService.getLessonsBySubject(subjectId)
        );
    }
}
