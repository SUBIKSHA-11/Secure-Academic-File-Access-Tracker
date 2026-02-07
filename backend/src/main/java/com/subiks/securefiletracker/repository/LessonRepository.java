package com.subiks.securefiletracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subiks.securefiletracker.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findBySubjectId(Long subjectId);
}
