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
import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.repository.FileRepository;
import com.subiks.securefiletracker.repository.UserRepository;

@Service
public class FileService {

   private static final String UPLOAD_DIR =
        System.getProperty("user.dir") + "/uploads";

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;
@Autowired
private AccessLogService accessLogService;

    public FileEntity uploadFile(
            MultipartFile file,
            String sensitivity,
            String uploadedBy) throws IOException {

        File uploadFolder = new File(UPLOAD_DIR);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
boolean suspicious = false;

// rule 1: odd time
int hour = LocalDateTime.now().getHour();
if (hour >= 21) {
    suspicious = true;
}

// save log
accessLogService.log(
        uploadedBy,
        "UPLOAD",
        file.getOriginalFilename(),
        suspicious
);

        String storedFileName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        File dest = new File(uploadFolder, storedFileName);
        file.transferTo(dest);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginalFileName(file.getOriginalFilename());
        fileEntity.setStoredFileName(storedFileName);
        fileEntity.setSensitivity(sensitivity);
        fileEntity.setUploadedBy(uploadedBy);
        fileEntity.setUploadTime(LocalDateTime.now());

        return fileRepository.save(fileEntity);
        
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    // ROLE CHECK SUPPORT
    public String getUserRole(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRole)
                .orElse("UNKNOWN");
    }
    
}
