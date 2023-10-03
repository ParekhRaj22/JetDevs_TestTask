package com.example.jetdevs.fileUploader.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jetdevs.fileUploader.domain.File;
import com.example.jetdevs.fileUploader.domain.FileRecord;
import com.example.jetdevs.fileUploader.vo.FileProgressVO;

@Service
public interface FileService {

	void save(MultipartFile file, String username);

	List<File> getAllFiles();

	List<FileRecord> getFileRecords(long fileId, String username);

	void deleteFileAndRecords(long fileId);

	FileProgressVO getFileProgressStatus(Long fileId);

}