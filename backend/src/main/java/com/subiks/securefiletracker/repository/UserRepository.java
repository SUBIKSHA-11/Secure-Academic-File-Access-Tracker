package com.subiks.securefiletracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subiks.securefiletracker.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
