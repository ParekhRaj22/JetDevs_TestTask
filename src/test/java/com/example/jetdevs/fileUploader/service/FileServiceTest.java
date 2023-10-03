package com.example.jetdevs.fileUploader.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import com.example.jetdevs.fileUploader.domain.File;
import com.example.jetdevs.fileUploader.domain.FileRecord;
import com.example.jetdevs.fileUploader.domain.FileReview;
import com.example.jetdevs.fileUploader.domain.User;
import com.example.jetdevs.fileUploader.exception.RecordNotExistException;
import com.example.jetdevs.fileUploader.repository.FileRepository;
import com.example.jetdevs.fileUploader.repository.FileReviewRepository;
import com.example.jetdevs.fileUploader.repository.RecordRepository;
import com.example.jetdevs.fileUploader.vo.FileProgressVO;

@SpringBootTest
public class FileServiceTest {

	@Autowired
	private FileService fileUploadService;

	@MockBean
	private AuthService authService;

	@MockBean
	private FileRepository fileRepository;

	@MockBean
	private RecordRepository recordRepository;

	@MockBean
	private FileReviewRepository reviewRepository;

	@Test
	public void testListFiles() {
		List<File> testFileEntity = new ArrayList<>();
		when(fileRepository.findAll()).thenReturn(testFileEntity);
		List<File> result = fileUploadService.getAllFiles();
		assertEquals(testFileEntity.size(), result.size());
		verify(fileRepository, times(1)).saveAll(testFileEntity);
	}

	@Test
	public void testNonExcelFileUpload() throws Exception {
		Resource resource = new ClassPathResource("test.txt");
		MockMultipartFile multipartFile = new MockMultipartFile("test.txt", "test.txt", "",
				resource.getContentAsByteArray());
		assertThrowsExactly(RuntimeException.class, () -> fileUploadService.save(multipartFile, "Raj"));
	}

	@Test
	public void testSaveFile() throws Exception {
		Resource resource = new ClassPathResource("testing.xlsx");
		MockMultipartFile multipartFileToSave = new MockMultipartFile("testing.xlsx", "testing.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", resource.getContentAsByteArray());
		String username = "Raj";
		User user = new User();
		user.setUsername(username);
		when(authService.getUserByUserName(username)).thenReturn(user);
		fileUploadService.save(multipartFileToSave, username);
		verify(fileRepository, times(1)).save(any(File.class));
		verify(recordRepository, times(1)).saveAll(anyList());

	}

	@Test
	public void testGetFileRecords() {
		long fileId = 11L;
		String username = "Raj";
		User mockUser = new User();
		mockUser.setUsername(username);
		File fileEntityForRecords = new File();
		fileEntityForRecords.setFileId(fileId);
		FileReview fileForReview = new FileReview();
		fileForReview.setFile(fileEntityForRecords);
		List<FileRecord> recordsForFile = new ArrayList<>();
		FileRecord record1 = new FileRecord();
		record1.setFile(fileEntityForRecords);
		recordsForFile.add(record1);

		when(authService.getUserByUserName(username)).thenReturn(mockUser);
		when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntityForRecords));
		when(recordRepository.getRecordByFileId(fileId)).thenReturn(recordsForFile);

		List<FileRecord> result = fileUploadService.getFileRecords(fileId, username);
		assertNotNull(result);
		verify(fileRepository, times(1)).save(fileEntityForRecords);
		verify(recordRepository, times(1)).getRecordByFileId(fileId);

	}

	@Test
	public void testGetFileRecordsInvalidFileId() {
		long fileId = 1L;
		String username = "Raj";
		when(fileRepository.findById(fileId)).thenReturn(Optional.empty());
		assertThrowsExactly(RecordNotExistException.class, () -> fileUploadService.getFileRecords(fileId, username));
	}

	@Test
	public void testDeleteFileAndRecords() {
		long fileId = 10L;
		File fileToBeDeleted = new File();
		when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileToBeDeleted));
		doNothing().when(fileRepository).deleteById(fileId);
		fileUploadService.deleteFileAndRecords(fileId);
		verify(fileRepository, times(1)).deleteById(fileId);
	}

	@Test
	public void testGetFileProgressStatus() {
		// Arrange
		long fileId = 11L;
		File fileEntity = new File();
		when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

		// Act
		FileProgressVO result = fileUploadService.getFileProgressStatus(fileId);

		// Assert
		assertNotNull(result);
		assertEquals(fileEntity.getStatus(), result.getStatus());
		verify(fileRepository, times(1)).findById(fileId);
	}
}
