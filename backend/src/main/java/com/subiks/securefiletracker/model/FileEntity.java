package com.subiks.securefiletracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;
    private String storedFileName;

    private String uploadedBy;   // faculty email
    private String sensitivity;  // LOW / HIGH

    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOriginalFileName() { return originalFileName; }
    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getStoredFileName() { return storedFileName; }
    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getSensitivity() { return sensitivity; }
    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public LocalDateTime getUploadTime() { return uploadTime; }
    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Lesson getLesson() { return lesson; }
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
