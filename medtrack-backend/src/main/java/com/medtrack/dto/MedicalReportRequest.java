package com.medtrack.dto;

import java.time.LocalDateTime;
public interface MedicalReportRequest {

Long getPatientId();

Long getDoctorId();

String getReportType();

String getTitle();

String getDescription();

String getFileUrl();

LocalDateTime getReportDate();
}
