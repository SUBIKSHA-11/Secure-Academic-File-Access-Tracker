package com.subiks.securefiletracker.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.subiks.securefiletracker.model.FileEntity;
import com.subiks.securefiletracker.model.Lesson;
import com.subiks.securefiletracker.repository.FileRepository;
import com.subiks.securefiletracker.repository.LessonRepository;

@Service
public class FileService {
private static final String UPLOAD_DIR =
        System.getProperty("user.dir") + "/uploads";


    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private LessonRepository lessonRepository;

    // FACULTY upload
    public FileEntity uploadFile(
            MultipartFile file,
            Long lessonId,
            String sensitivity,
            String uploadedBy) throws IOException {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        File uploadFolder = new File(UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir();
        }

        String storedName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        File dest = new File(uploadFolder, storedName);
        file.transferTo(dest);

        FileEntity entity = new FileEntity();
        entity.setOriginalFileName(file.getOriginalFilename());
        entity.setStoredFileName(storedName);
        entity.setLesson(lesson);
        entity.setUploadedBy(uploadedBy);
        entity.setSensitivity(sensitivity);
        entity.setUploadTime(LocalDateTime.now());

        return fileRepository.save(entity);
    }

    // STUDENT fetch lesson-wise files
    public List<FileEntity> getFilesByLesson(Long lessonId) {
        return fileRepository.findByLessonId(lessonId);
    }
    public List<FileEntity> searchFiles(
        Long subjectId,
        Long semesterId,
        String sensitivity) {

    return fileRepository.searchFiles(
            subjectId,
            semesterId,
            sensitivity
    );
}
public FileEntity getFileById(Long fileId) {
    return fileRepository.findById(fileId).orElse(null);
}
public void deleteFile(Long fileId, String facultyEmail) {

    FileEntity file = fileRepository.findById(fileId)
            .orElseThrow(() -> new RuntimeException("File not found"));

    // faculty can delete only their files
    if (!file.getUploadedBy().equals(facultyEmail)) {
        throw new RuntimeException("Unauthorized delete");
    }

    File diskFile = new File(
            System.getProperty("user.dir") + "/uploads/" +
            file.getStoredFileName()
    );

    if (diskFile.exists()) {
        diskFile.delete();
    }

    fileRepository.delete(file);
}


}
