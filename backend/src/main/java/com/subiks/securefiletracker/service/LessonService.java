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

    public List<Lesson> getLessonsBySubject(Long subjectId) {
        return lessonRepository.findBySubjectId(subjectId);
    }

    public void updateLessonName(Long lessonId, String name) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
        lesson.setName(name);
        lessonRepository.save(lesson);
    }

    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

@Autowired
private SubjectRepository subjectRepository;

public void addLesson(String name, Long subjectId) {

    Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found"));

    Lesson lesson = new Lesson();
    lesson.setName(name);
    lesson.setSubject(subject);

    lessonRepository.save(lesson);
}

}
