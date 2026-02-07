package com.subiks.securefiletracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.model.Lesson;
import com.subiks.securefiletracker.model.Subject;
import com.subiks.securefiletracker.repository.LessonRepository;
import com.subiks.securefiletracker.repository.SubjectRepository;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Lesson addLesson(Long subjectId, String name) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Lesson lesson = new Lesson();
        lesson.setName(name);
        lesson.setSubject(subject);

        return lessonRepository.save(lesson);
    }

    public List<Lesson> getLessonsBySubject(Long subjectId) {
        return lessonRepository.findBySubjectId(subjectId);
    }
}
