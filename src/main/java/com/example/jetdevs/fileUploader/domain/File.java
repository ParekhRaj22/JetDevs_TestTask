package com.example.jetdevs.fileUploader.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id", nullable = false)
	private Long fileId;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
	@Column(name = "upload_timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp uploadTimestamp;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
	@Column(name = "last_access_time")
	private Date lastAccessTime;

	private String status;

	@ManyToOne
	@JoinColumn(name = "last_reviewed_by", referencedColumnName = "user_id")
	private User lastReviewedBy;

	@OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FileRecord> records;

	@OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<FileReview> reviews;

}
