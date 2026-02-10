package com.subiks.securefiletracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.subiks.securefiletracker.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    long countBySuspiciousTrue();
List<AccessLog> findByUserEmailOrderByTimestampDesc(String userEmail);

@Query("""
SELECT DATE(a.timestamp), COUNT(a)
FROM AccessLog a
GROUP BY DATE(a.timestamp)
ORDER BY DATE(a.timestamp)
""")
List<Object[]> countLogsPerDay();
List<AccessLog> findAllByOrderByTimestampDesc();
List<AccessLog> findBySuspiciousTrueOrderByTimestampDesc();

@Query("""
SELECT a FROM AccessLog a
WHERE YEAR(a.timestamp) = :year
""")
List<AccessLog> findByYear(int year);
@Query("""
SELECT u.department.name, COUNT(a)
FROM AccessLog a
JOIN User u ON a.userEmail = u.email
GROUP BY u.department.name
""")
List<Object[]> getDepartmentWiseAccessCount();
@Query("""
SELECT u.department.name, COUNT(a)
FROM AccessLog a
JOIN User u ON a.userEmail = u.email
WHERE u.role = 'STUDENT'
GROUP BY u.department.name
""")
List<Object[]> studentAccessByDepartment();


@Query("""
SELECT u.department.name, COUNT(a)
FROM AccessLog a
JOIN User u ON a.userEmail = u.email
WHERE u.role = 'FACULTY'
GROUP BY u.department.name
""")
List<Object[]> facultyAccessByDepartment();

}