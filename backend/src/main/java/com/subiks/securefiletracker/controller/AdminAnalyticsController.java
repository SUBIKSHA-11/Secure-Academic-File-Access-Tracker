package com.subiks.securefiletracker.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.service.AdminAnalyticsService;

@RestController
@RequestMapping("/admin/analytics")
@CrossOrigin
public class AdminAnalyticsController {

    @Autowired
    private AdminAnalyticsService analyticsService;

    @GetMapping
    public Map<String, Long> getStats() {
        return Map.of(
            "students", analyticsService.getStudentCount(),
            "faculty", analyticsService.getFacultyCount(),
            "files", analyticsService.getFileCount(),
            "suspicious", analyticsService.getSuspiciousCount()
        );
    }
}
