package com.example.jetdevs.fileUploader.vo;

import java.sql.Timestamp;
import java.util.Date;

import com.example.jetdevs.fileUploader.domain.File;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class FileVO {
	private Long fileId;
	private String fileName;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
	private Timestamp uploadTimestamp;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
	private Date lastAccessTime;
	private String status;

	public FileVO(File fileEntity) {
		this.fileId = fileEntity.getFileId();
		this.fileName = fileEntity.getFileName();
		this.uploadTimestamp = fileEntity.getUploadTimestamp();
		this.lastAccessTime = fileEntity.getLastAccessTime();
		this.status = fileEntity.getStatus();
	}
}
