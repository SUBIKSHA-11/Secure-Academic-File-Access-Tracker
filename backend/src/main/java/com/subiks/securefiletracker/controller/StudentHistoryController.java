package com.subiks.securefiletracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.AccessLog;
import com.subiks.securefiletracker.repository.AccessLogRepository;

@RestController
@RequestMapping("/student/history")
@CrossOrigin
@PreAuthorize("hasRole('STUDENT')")
public class StudentHistoryController {

    @Autowired
    private AccessLogRepository accessLogRepository;

    @GetMapping
    public List<AccessLog> getHistory(Authentication auth) {
        return accessLogRepository
                .findByUserEmailOrderByTimestampDesc(auth.getName());
    }
}

