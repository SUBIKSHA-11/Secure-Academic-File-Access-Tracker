package com.subiks.securefiletracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subiks.securefiletracker.repository.AccessLogRepository;
import com.subiks.securefiletracker.repository.FileRepository;
import com.subiks.securefiletracker.repository.UserRepository;
@Service
public class AdminAnalyticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AccessLogRepository accessLogRepository;

    public long getStudentCount() {
        return userRepository.countByRole("STUDENT");
    }

    public long getFacultyCount() {
        return userRepository.countByRole("FACULTY");
    }

    public long getFileCount() {
        return fileRepository.count();
    }

    public long getSuspiciousCount() {
        return accessLogRepository.countBySuspiciousTrue();
    }
       public List<Object[]> getAccessLogGraph() {
        return accessLogRepository.countLogsPerDay();
       }
        public List<Object[]> getDepartmentWiseStats() {
    return userRepository.getDepartmentWiseCounts();

    }

public List<Object[]> getDepartmentWiseAccess() {
    return accessLogRepository.getDepartmentWiseAccessCount();
}
public List<Object[]> getStudentAccessDeptWise() {
    return accessLogRepository.studentAccessByDepartment();
}

public List<Object[]> getFacultyAccessDeptWise() {
    return accessLogRepository.facultyAccessByDepartment();
}

}
