package com.subiks.securefiletracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subiks.securefiletracker.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    long countBySuspiciousTrue();
List<AccessLog> findByUserEmailOrderByTimestampDesc(String userEmail);

}
