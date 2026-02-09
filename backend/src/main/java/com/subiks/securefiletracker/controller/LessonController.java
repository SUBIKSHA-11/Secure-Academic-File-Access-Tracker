package com.subiks.securefiletracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.service.LessonService;

@RestController
@RequestMapping("/lessons")
@CrossOrigin
public class LessonController {

    @Autowired
    private LessonService lessonService;

    // ===== UPDATE LESSON NAME =====
    @PreAuthorize("hasAuthority('ROLE_FACULTY')")
    @PutMapping("/{lessonId}")
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<?> updateLesson(
            @PathVariable Long lessonId,
            @RequestParam String name) {

        try {
            lessonService.updateLessonName(lessonId, name);
            return ResponseEntity.ok("Lesson updated");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Update failed: " + e.getMessage());
        }
    }

    // ===== DELETE LESSON =====
    @PreAuthorize("hasAuthority('ROLE_FACULTY')")
    @DeleteMapping("/{lessonId}")
    @SuppressWarnings("CallToPrintStackTrace")
    public ResponseEntity<?> deleteLesson(@PathVariable Long lessonId) {

        try {
            lessonService.deleteLesson(lessonId);
            return ResponseEntity.ok("Lesson deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Delete failed: " + e.getMessage());
        }
    }
    @PreAuthorize("hasAuthority('ROLE_FACULTY')")
@PostMapping("/add")
public ResponseEntity<?> addLesson(
        @RequestParam String name,
        @RequestParam Long subjectId) {

    lessonService.addLesson(name, subjectId);
    return ResponseEntity.ok("Lesson added");
}

}
