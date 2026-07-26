package com.medtrack.service;
import org.springframework.stereotype.Service;
import com.medtrack.dto.CreateMedicalReportRequest;
import com.medtrack.dto.MedicalReportResponse;
import com.medtrack.entity.Doctor;
import com.medtrack.entity.MedicalReport;
import com.medtrack.entity.Patient;
import com.medtrack.enums.ReportType;
import com.medtrack.repository.DoctorRepository;
import com.medtrack.repository.MedicalReportRepository;
import com.medtrack.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
@Service

public class MedicalReportService {
    private final MedicalReportRepository medicalReportRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public MedicalReportService(
            MedicalReportRepository medicalReportRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        this.medicalReportRepository = medicalReportRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }
    @Transactional
    public MedicalReportResponse create(CreateMedicalReportRequest request) {
        Patient patient = patientRepository.findByIdOrThrow(request.getPatientId());

        Doctor doctor = null;
        if (request.getDoctorId() != null) {
            doctor = doctorRepository.findByIdOrThrow(request.getDoctorId());
        }

        MedicalReport report = new MedicalReport();
        report.setPatient(patient);
        report.setDoctor(doctor);
        report.setReportType(ReportType.valueOf(request.getReportType()));
        report.setTitle(request.getTitle());
        report.setDescription(request.getDescription());
        report.setFileUrl(request.getFileUrl());
        report.setReportDate(request.getReportDate());

        report = medicalReportRepository.saveAndFlush(report);
        MedicalReportResponse response = new MedicalReportResponse();
        response.setId(report.getId());
        response.setPatientId(report.getPatient().getId());

        if (report.getDoctor() != null) {
            response.setDoctorId(report.getDoctor().getId());
        }

        response.setReportType(report.getReportType().name());
        response.setTitle(report.getTitle());
        response.setDescription(report.getDescription());
        response.setFileUrl(report.getFileUrl());
        response.setReportDate(report.getReportDate());
        response.setCreatedAt(report.getCreatedAt());
        response.setUpdatedAt(report.getUpdatedAt());

        return response;

    }
}