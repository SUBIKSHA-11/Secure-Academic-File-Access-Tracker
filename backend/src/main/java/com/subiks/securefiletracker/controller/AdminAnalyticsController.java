package com.subiks.securefiletracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subiks.securefiletracker.model.AccessLog;
import com.subiks.securefiletracker.repository.AccessLogRepository;
import com.subiks.securefiletracker.repository.FileRepository;
import com.subiks.securefiletracker.service.AdminAnalyticsService;

@RestController
@RequestMapping("/admin/analytics")
@CrossOrigin
@PreAuthorize("hasAuthority('ROLE_ADMIN')")

public class AdminAnalyticsController {

    @Autowired
    private AdminAnalyticsService analyticsService;

    @Autowired
    private AccessLogRepository accessLogRepository;
    @Autowired
    private FileRepository fileRepository;
    @GetMapping
    public Map<String, Long> getStats() {
        return Map.of(
            "students", analyticsService.getStudentCount(),
            "faculty", analyticsService.getFacultyCount(),
            "files", analyticsService.getFileCount(),
            "suspicious", analyticsService.getSuspiciousCount()
        );
    }

    @GetMapping("/access-graph")
    public List<Object[]> accessGraph() {
        return analyticsService.getAccessLogGraph();
    }

    @GetMapping("/logs")
    public List<AccessLog> getAllLogs() {
        return accessLogRepository.findAllByOrderByTimestampDesc();
    }

    @GetMapping("/suspicious")
    public List<AccessLog> getSuspiciousLogs() {
        return accessLogRepository.findBySuspiciousTrueOrderByTimestampDesc();
    }
    @GetMapping("/department-stats")
public List<Object[]> departmentStats() {
    return analyticsService.getDepartmentWiseStats();
}
@GetMapping("/logs/year/{year}")
public List<AccessLog> logsByYear(@PathVariable int year) {
    return accessLogRepository.findByYear(year);
}

@GetMapping("/logs/suspicious")
public List<AccessLog> suspiciousLogs() {
    return accessLogRepository.findBySuspiciousTrueOrderByTimestampDesc();
}
@GetMapping("/file-downloads")
public List<Object[]> fileDownloadStats() {
    return fileRepository.getDownloadStats();
}
@GetMapping("/faculty-workload")
public List<Object[]> facultyWorkload() {
    return fileRepository.getFacultyWorkload();
}
@GetMapping("/access-department")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public List<Object[]> departmentWiseAccess() {
    return analyticsService.getDepartmentWiseAccess();
}
@GetMapping("/access/student-department")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public List<Object[]> studentAccessDeptWise() {
    return analyticsService.getStudentAccessDeptWise();
}

@GetMapping("/access/faculty-department")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public List<Object[]> facultyAccessDeptWise() {
    return analyticsService.getFacultyAccessDeptWise();
}

}
