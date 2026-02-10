package com.subiks.securefiletracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.subiks.securefiletracker.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    long countByRole(String role);
    List<User> findByRole(String role);
@Query("""
SELECT u.department.name,
SUM(CASE WHEN u.role = 'STUDENT' THEN 1 ELSE 0 END),
SUM(CASE WHEN u.role = 'FACULTY' THEN 1 ELSE 0 END)
FROM User u
GROUP BY u.department.name
""")
List<Object[]> getDepartmentWiseCounts();

List<User> findByDepartmentIdAndRole(Long departmentId, String role);

List<User> findByRoleAndDepartmentIdAndSemesterIdOrderByNameAsc(
    String role,
    Long departmentId,
    Long semesterId
);

}
