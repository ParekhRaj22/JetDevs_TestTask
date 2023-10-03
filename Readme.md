# File Uploader Application

This application allows users to upload records from Excel files, list uploaded files, review specific files, and delete files with associated records. It provides endpoints for various operations and ensures data integrity.

## Prerequisites

- Java (version 1.8)
- Spring Boot (version 3.1.4)
- Maven 
- MySQL 

## Getting Started

1. Clone the repository: `git clone <repository-url>`
2. Configure your database settings in `application.properties`.
3. Insert the queries present under `'resources\data.sql'`
4. Build the project: `mvn clean install` 

## API Endpoints

### 1. Upload Records from Excel File

**Endpoint:** `POST /api/v1/upload-file`
**Request:** `multipart/form-data` with Excel file
**Response:** Upload status

### 2. Check Upload Progress

**Endpoint:** `GET /api/v1/file-upload-progress/{fileId}`
**Response:** Upload progress status

### 3. List Uploaded Files

**Endpoint:** `GET /api/v1/files`
**Response:** List of uploaded files

### 4. Review Specific File

**Endpoint:** `GET /api/v1/file-records/{fileId}`
**Response:** List of records for the specified file

### 5. Delete Specific File

**Endpoint:** `DELETE /api/v1/files/{fileId}`
**Response:** Deletion status

## Authentication and Authorization
The application supports JWT-based authentication. Users and roles are defined to control access to specific APIs. Below are the roles:

ROLE_USER: Grants access to basic functionalities.
ROLE_ADMIN: Grants access to administrative functionalities.

### User Authentication Endpoints

1. User Login
**Endpoint:** `POST /api/auth/login`
**Request:** JSON payload with username and password
**Response:** JWT token upon successful login

2. User Registration
**Endpoint:** `POST /api/auth/register`
**Request:** JSON payload with name, password, and role
**Response:** Details of the registered user

3. User Roles

- `ROLE_USER`: Grants access to basic functionalities.
- `ROLE_ADMIN`: Grants access to administrative functionalities.

## Data Model

### File

- `fileId`: Unique identifier for the file
- `fileName`: Name of the uploaded file
- `uploadTimestamp`: Timestamp of the file upload
- `lastAccessTime`: Timestamp of the last access to the file
- `status`: Upload status of the file
- `lastReviewedBy`: User who last reviewed the file
- `records`: List of records associated with the file
- `reviews`: List of reviews for the file

### FileRecord

- `id`: Unique identifier for the record
- `file`: FileEntity associated with the record
- `productName`: Name of the product
- `productDesc`: Description of the product
- `productPrice`: Price of the product

### FileReview

- `id`: Unique identifier for the review
- `file`: FileEntity associated with the review
- `user`: User who reviewed the file
- `accessTime`: Timestamp of the file review

## Contributing

1. Fork the repository
2. Create a new branch: `git checkout -b feature-name`
3. Make your changes and commit: `git commit -m 'Description of changes'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request
