package com.example.jetdevs.fileUploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jetdevs.fileUploader.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

}
