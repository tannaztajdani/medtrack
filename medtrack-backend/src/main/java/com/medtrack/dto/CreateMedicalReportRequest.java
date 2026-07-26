package com.medtrack.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
public class CreateMedicalReportRequest implements MedicalReportRequest {
    @NotNull
    private Long patientId;

    private Long doctorId;

    @NotBlank
    private String reportType;

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String fileUrl;

    @NotNull
    private LocalDateTime reportDate;

    @Override
    public Long getPatientId() {
        return patientId;
    }

    @Override
    public Long getDoctorId() {
        return doctorId;
    }

    @Override
    public String getReportType() {
        return reportType;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getFileUrl() {
        return fileUrl;
    }

    @Override
    public LocalDateTime getReportDate() {
        return reportDate;
    }
}

