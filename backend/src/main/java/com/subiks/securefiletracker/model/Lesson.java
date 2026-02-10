package com.subiks.securefiletracker.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Unit 1 – Introduction

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> files;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }
    public List<FileEntity> getFiles() {
    return files;
}

}
