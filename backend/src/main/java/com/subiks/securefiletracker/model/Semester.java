package com.subiks.securefiletracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "semesters")
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number; // 1,2,3,4,5,6,7,8

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) {
        this.department = department;
    }
}
