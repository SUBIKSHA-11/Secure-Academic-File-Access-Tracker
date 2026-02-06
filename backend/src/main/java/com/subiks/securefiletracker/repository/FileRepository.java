package com.subiks.securefiletracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subiks.securefiletracker.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
