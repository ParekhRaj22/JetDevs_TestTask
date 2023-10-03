package com.example.jetdevs.fileUploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jetdevs.fileUploader.domain.FileReview;

public interface FileReviewRepository extends JpaRepository<FileReview, Long> {

}
