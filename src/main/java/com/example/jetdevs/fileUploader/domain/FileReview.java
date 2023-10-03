package com.example.jetdevs.fileUploader.domain;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "file_id")
	private File file;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "access_time", nullable = false)
	private Date accessTime;
}
