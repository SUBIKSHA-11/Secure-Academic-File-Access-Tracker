package com.subiks.securefiletracker.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.model.AccessLog;
import com.subiks.securefiletracker.repository.AccessLogRepository;

@Service
public class AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public void log(String email, String action, String fileName, boolean suspicious) {
        AccessLog log = new AccessLog();
        log.setUserEmail(email);
        log.setAction(action);
        log.setFileName(fileName);
        log.setSuspicious(suspicious);
        log.setTimestamp(LocalDateTime.now());

        accessLogRepository.save(log);
    }
}
