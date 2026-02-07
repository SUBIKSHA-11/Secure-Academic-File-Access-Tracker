package com.subiks.securefiletracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.subiks.securefiletracker.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByLessonId(Long lessonId);
    @Override
    long count();
    @Query("""
SELECT f FROM FileEntity f
WHERE f.lesson.subject.id = :subjectId
AND f.lesson.subject.semester.id = :semesterId
AND (:sensitivity IS NULL OR f.sensitivity = :sensitivity)
""")
List<FileEntity> searchFiles(
    Long subjectId,
    Long semesterId,
    String sensitivity
);


}
