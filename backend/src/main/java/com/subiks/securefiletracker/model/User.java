package com.subiks.securefiletracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
private String regNo;

    @Column(unique = true)
    private String email;

    private String password;

    private String role; // ADMIN, FACULTY, STUDENT

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester; // ONLY for students

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    @Column(length = 15)
   private String phone;

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}
public String getRegNo() { return regNo; }
public void setRegNo(String regNo) { this.regNo = regNo; }


}
