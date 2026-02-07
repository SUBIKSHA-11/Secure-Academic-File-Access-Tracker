package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.Lesson;
import com.subiks.securefiletracker.service.LessonService;

@RestController
@RequestMapping("/admin/lessons")
@CrossOrigin
public class LessonController {

    @Autowired
    private LessonService lessonService;

    // ADMIN adds lesson (unit)
    @PostMapping
    public ResponseEntity<?> addLesson(
            @RequestParam Long subjectId,
            @RequestParam String name) {

        return ResponseEntity.ok(
                lessonService.addLesson(subjectId, name)
        );
    }

    // STUDENT / FACULTY fetch lessons
    @GetMapping("/{subjectId}")
    public ResponseEntity<List<Lesson>> getLessons(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                lessonService.getLessonsBySubject(subjectId)
        );
    }
}
