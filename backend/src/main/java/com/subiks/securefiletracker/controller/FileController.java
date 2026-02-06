package com.subiks.securefiletracker.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.subiks.securefiletracker.model.FileEntity;
import com.subiks.securefiletracker.service.AccessLogService;
import com.subiks.securefiletracker.service.FileService;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    @Autowired
    private FileService fileService;
@Autowired
private AccessLogService accessLogService;

    // FILE UPLOAD API
   @PostMapping(value = "/upload", 
              consumes = "multipart/form-data")
public ResponseEntity<?> uploadFile(
    
        @RequestParam("file") MultipartFile file,
        @RequestParam("sensitivity") String sensitivity,
        Authentication authentication) {
             System.out.println("=== UPLOAD API HIT ===");
    /*System.out.println("File object: " + file);
    System.out.println("File name: " +
            (file != null ? file.getOriginalFilename() : "NULL"));
    System.out.println("Sensitivity: " + sensitivity);
    System.out.println("User: " +
            (authentication != null ? authentication.getName() : "NULL"));*/
if (file == null || file.isEmpty()) {
        return ResponseEntity.badRequest()
                .body("File is missing or empty");
    }
    if (authentication == null) {
    return ResponseEntity.status(401).body("Unauthorized");
}

String email = authentication.getName();

    String role = fileService.getUserRole(email);

    if ("STUDENT".equals(role)) {
        return ResponseEntity.status(403)
                .body("Students are not allowed to upload files");
    }

    try {
        FileEntity savedFile =
                fileService.uploadFile(file, sensitivity, email);

        return ResponseEntity.ok(savedFile);

    } catch (IOException e) {
        return ResponseEntity.badRequest()
                .body("File upload failed");
    }
}

    // FILE LIST API
@GetMapping("/list")
public ResponseEntity<?> listFiles() {
    return ResponseEntity.ok(fileService.getAllFiles());
}
@GetMapping("/download")
public ResponseEntity<?> downloadFile(
        @RequestParam("name") String fileName,
        Authentication authentication) {

    File file = new File("uploads/" + fileName);

    if (!file.exists()) {
        return ResponseEntity.badRequest().body("File not found");
    }

    try {
        byte[] data = java.nio.file.Files.readAllBytes(file.toPath());

        // log download
        accessLogService.log(
                authentication.getName(),
                "DOWNLOAD",
                fileName,
                false
        );

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=\"" + fileName + "\"")
                .body(data);

    } catch (IOException e) {
        return ResponseEntity.internalServerError()
                .body("Download failed");
    }
}
@GetMapping("/test")
public String test() {
    return "FILE CONTROLLER WORKING";
}

}
