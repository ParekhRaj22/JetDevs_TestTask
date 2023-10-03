package com.example.jetdevs.fileUploader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jetdevs.fileUploader.domain.FileRecord;


@Repository
public interface RecordRepository extends JpaRepository<FileRecord, Integer> {
	@Query("SELECT r FROM FileRecord r WHERE r.file.id = :fileId")
	List<FileRecord> getRecordByFileId(Long fileId);
}
