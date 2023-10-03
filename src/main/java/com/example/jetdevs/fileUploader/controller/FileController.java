package com.example.jetdevs.fileUploader.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.jetdevs.fileUploader.domain.File;
import com.example.jetdevs.fileUploader.domain.FileRecord;
import com.example.jetdevs.fileUploader.security.JwtTokenProvider;
import com.example.jetdevs.fileUploader.service.FileService;
import com.example.jetdevs.fileUploader.vo.FileProgressVO;
import com.example.jetdevs.fileUploader.vo.FileVO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1")
public class FileController extends BaseController {

	@Autowired
	private FileService fileUploadService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	@PostMapping(value="/upload-file", consumes = {"multipart/form-data"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name= "Bearer Authentication")
	public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
			String username = jwtTokenProvider.getUsername(token);
			try {
				fileUploadService.save(file, username);
			} catch (Exception e) {
				return error("There seems to be some issue with the file type", HttpStatus.INTERNAL_SERVER_ERROR, "/upload-file");
			}
			return success(file.getOriginalFilename(), "file has been uploaded");
	}
	
	
	@GetMapping("/file-upload-progress/{fileId}")
	@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name= "Bearer Authentication")
	public ResponseEntity<FileProgressVO> progress(@PathVariable Long fileId) {
		return success(fileUploadService.getFileProgressStatus(fileId), "Progress of file upload");
	}
	
	
	@GetMapping("/files")
	@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name= "Bearer Authentication")
	public ResponseEntity<List<FileVO>> fetchFiles() {
		List<File> fileEntities = fileUploadService.getAllFiles();
		List<FileVO> fileDtos = fileEntities.stream().map(FileVO::new).collect(Collectors.toList());
		return success(fileDtos, "List of uploaded files");
	}
	
	
	@GetMapping("/file-records/{fileId}")
	@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name= "Bearer Authentication")
	public ResponseEntity<List<FileRecord>> getFileRecords(@PathVariable long fileId,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		String username = jwtTokenProvider.getUsername(token);
		return success(fileUploadService.getFileRecords(fileId, username), "List of file records");

	}
	
	
	@DeleteMapping("/files/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name= "Bearer Authentication")
	public ResponseEntity<?> deleteFileAndRecords(@PathVariable("id") long fileId) {
		try {
			fileUploadService.deleteFileAndRecords(fileId);
			return success(null, "deleted successfully");
		} catch (Exception e) {
			return error("File with file id: " + fileId + " does not exist ", HttpStatus.NOT_FOUND, "/delete-file");
		}
	}
}
