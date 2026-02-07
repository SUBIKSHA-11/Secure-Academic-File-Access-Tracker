package com.subiks.securefiletracker.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.subiks.securefiletracker.model.FileEntity;
import com.subiks.securefiletracker.service.FileService;

@RestController
@RequestMapping("/files")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;
    //private FileRepository fileRepository;

    // FACULTY upload
    @PreAuthorize("hasRole('FACULTY')")

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam MultipartFile file,
            @RequestParam Long lessonId,
            @RequestParam String sensitivity,
            Authentication auth) {

        try {
            return ResponseEntity.ok(
                    fileService.uploadFile(
                            file,
                            lessonId,
                            sensitivity,
                            auth.getName()
                    )
            );
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("Upload failed"+ e.getMessage());
        }
    }

    // STUDENT fetch lesson-wise files
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<FileEntity>> getFiles(
            @PathVariable Long lessonId) {

        return ResponseEntity.ok(
                fileService.getFilesByLesson(lessonId)
        );
    }
    @PreAuthorize("hasRole('STUDENT')")
   @GetMapping("/search")
public ResponseEntity<List<FileEntity>> searchFiles(
        @RequestParam Long subjectId,
        @RequestParam Long semesterId,
        @RequestParam(required = false) String sensitivity) {

    return ResponseEntity.ok(
            fileService.searchFiles(subjectId, semesterId, sensitivity)
    );
}
// STUDENT / FACULTY download file
@PreAuthorize("hasAnyRole('STUDENT','FACULTY','ADMIN')")
@GetMapping("/download/{fileId}")
public ResponseEntity<?> downloadFile(
        @PathVariable Long fileId,
        Authentication authentication) {

    try {
        FileEntity fileEntity = fileService.getFileById(fileId);

        if (fileEntity == null) {
            return ResponseEntity.badRequest()
                    .body("File not found");
        }

        File file = new File(
                System.getProperty("user.dir")
                        + "/uploads/"
                        + fileEntity.getStoredFileName()
        );

        if (!file.exists()) {
            return ResponseEntity.badRequest()
                    .body("File not found on server");
        }

        byte[] data = Files.readAllBytes(file.toPath());

        return ResponseEntity.ok()
                .header(
                    "Content-Disposition",
                    "attachment; filename=\"" 
                    + fileEntity.getOriginalFileName() + "\""
                )
                .body(data);

    } catch (IOException e) {
        return ResponseEntity.internalServerError()
                .body("Download failed: " + e.getMessage());
    }
}


}
