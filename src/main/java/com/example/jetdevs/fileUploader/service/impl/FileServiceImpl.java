package com.example.jetdevs.fileUploader.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jetdevs.fileUploader.config.JacksonConfig;
import com.example.jetdevs.fileUploader.constant.FileUploadStatus;
import com.example.jetdevs.fileUploader.domain.File;
import com.example.jetdevs.fileUploader.domain.FileRecord;
import com.example.jetdevs.fileUploader.domain.FileReview;
import com.example.jetdevs.fileUploader.domain.User;
import com.example.jetdevs.fileUploader.exception.RecordNotExistException;
import com.example.jetdevs.fileUploader.helper.ExcelHelper;
import com.example.jetdevs.fileUploader.repository.FileRepository;
import com.example.jetdevs.fileUploader.repository.FileReviewRepository;
import com.example.jetdevs.fileUploader.repository.RecordRepository;
import com.example.jetdevs.fileUploader.service.AuthService;
import com.example.jetdevs.fileUploader.service.FileService;
import com.example.jetdevs.fileUploader.vo.FileProgressVO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private AuthService authService;

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private FileReviewRepository fileReviewRepository;

	@Autowired
	private JacksonConfig objectMapper;

	@Override
	public void save(MultipartFile file, String username) {
		User user = authService.getUserByUserName(username);
		if(ExcelHelper.checkExcelFormat(file)) {			
			try {
				File fileEntity = new File();
				fileEntity.setFileName(file.getOriginalFilename());
				long currentTime = System.currentTimeMillis();
				fileEntity.setUploadTimestamp(new Timestamp(currentTime));
				fileEntity.setLastAccessTime(new Date());
				fileEntity.setLastReviewedBy(user);
				fileEntity.setStatus(FileUploadStatus.IN_PROGRESS);
				File UploadedFile = fileRepository.save(fileEntity);
				List<FileRecord> records = ExcelHelper.convertExcelToListOfRecord(file.getInputStream(), UploadedFile);
				List<FileRecord> saveAll = recordRepository.saveAll(records);
			} catch (IOException e) {
				throw new RuntimeException("Error while processing file");
			}
		} else {
			throw new RuntimeException("Please upload an excel file");
		}
	}

	@Override
	public List<File> getAllFiles() {
		List<File> fileEntities = fileRepository.findAll();
		for (File fileEntity : fileEntities) {
			fileEntity.setStatus(FileUploadStatus.SUCCESS);
		}
		try {
			String jsonString = objectMapper.objectMapper().writeValueAsString(fileEntities);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		fileRepository.saveAll(fileEntities);
		return fileEntities;
	}

	@Override
	public List<FileRecord> getFileRecords(long fileId, String username) {
		User user = authService.getUserByUserName(username);
		File fileInfo = fileRepository.findById(fileId)
				.orElseThrow(() -> new RecordNotExistException("Invalid file id"));
		fileInfo.setLastAccessTime(new Date());

		FileReview fileReview = new FileReview();
		fileReview.setFile(fileInfo);
		fileReview.setUser(user);
		fileReview.setAccessTime(new Date());
		fileReviewRepository.save(fileReview);
		fileInfo.setLastReviewedBy(user);
		fileRepository.save(fileInfo);
		List<FileRecord> records = recordRepository.getRecordByFileId(fileId);
		if (records.isEmpty()) {
			throw new RuntimeException("File is not present with id " + fileId);
		}
		return records;
	}

	@Override
	public void deleteFileAndRecords(long fileId) {
		fileRepository.findById(fileId)
				.orElseThrow(() -> new RecordNotExistException("File with id " + fileId + " is not present"));
		fileRepository.deleteById(fileId);
	}

	@Override
	public FileProgressVO getFileProgressStatus(Long fileId) {
		File fileStatus = fileRepository.findById(fileId)
				.orElseThrow(() -> new RecordNotExistException("Invalid file id"));
		FileProgressVO fileProgressDto = new FileProgressVO(fileStatus.getStatus());
		return fileProgressDto;
	}
}
