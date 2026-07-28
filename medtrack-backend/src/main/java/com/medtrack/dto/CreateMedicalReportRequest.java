package com.medtrack.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class CreateMedicalReportRequest {

    @NotNull(message = "Patient ID is required")
    @Positive(message = "Patient ID must be a positive number")
    private Long patientId;

    @Positive(message = "Doctor ID must be a positive number")
    private Long doctorId;

    @NotBlank(message = "Report type is required")
    private String reportType;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "File URL is required")
    @Size(max = 500, message = "File URL cannot exceed 500 characters")
    @Pattern(
            regexp = "^(https?://).+",
            message = "File URL must be a valid HTTP or HTTPS URL"
    )
    private String fileUrl;

    @NotNull(message = "Report date is required")
    private LocalDateTime reportDate;

    public Long getPatientId() {
        return patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getReportType() {
        return reportType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }
}