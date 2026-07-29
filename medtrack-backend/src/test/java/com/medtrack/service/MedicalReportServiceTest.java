package com.medtrack.service;

import com.medtrack.dto.CreateMedicalReportRequest;
import com.medtrack.dto.MedicalReportResponse;
import com.medtrack.entity.Doctor;
import com.medtrack.entity.MedicalReport;
import com.medtrack.entity.Patient;
import com.medtrack.enums.ReportType;
import com.medtrack.repository.DoctorRepository;
import com.medtrack.repository.MedicalReportRepository;
import com.medtrack.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalReportServiceTest {

    @Mock
    private MedicalReportRepository medicalReportRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private MedicalReportService medicalReportService;

    @Test
    void create_shouldCreateMedicalReportSuccessfully() {

        // Arrange
        CreateMedicalReportRequest request = new CreateMedicalReportRequest();
        request.setPatientId(1L);
        request.setDoctorId(2L);
        request.setReportType("LAB");
        request.setTitle("Blood Test");
        request.setDescription("Routine blood test");
        request.setFileUrl("https://example.com/report.pdf");
        request.setReportDate(LocalDateTime.of(2026, 1, 1, 10, 0));

        Patient patient = new Patient();
        patient.setId(1L);

        Doctor doctor = new Doctor();
        doctor.setId(2L);

        MedicalReport savedReport = new MedicalReport();
        savedReport.setId(100L);
        savedReport.setPatient(patient);
        savedReport.setDoctor(doctor);
        savedReport.setReportType(ReportType.LAB);
        savedReport.setTitle(request.getTitle());
        savedReport.setDescription(request.getDescription());
        savedReport.setFileUrl(request.getFileUrl());
        savedReport.setReportDate(request.getReportDate());

        when(patientRepository.findByIdOrThrow(1L)).thenReturn(patient);
        when(doctorRepository.findByIdOrThrow(2L)).thenReturn(doctor);
        when(medicalReportRepository.saveAndFlush(any(MedicalReport.class)))
                .thenReturn(savedReport);

        // Act
        MedicalReportResponse response = medicalReportService.create(request);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(100L, response.getId());
        Assertions.assertEquals(1L, response.getPatientId());
        Assertions.assertEquals(2L, response.getDoctorId());
        Assertions.assertEquals("LAB", response.getReportType());
        Assertions.assertEquals("Blood Test", response.getTitle());
        Assertions.assertEquals("Routine blood test", response.getDescription());
        Assertions.assertEquals("https://example.com/report.pdf", response.getFileUrl());
        Assertions.assertEquals(request.getReportDate(), response.getReportDate());

        verify(patientRepository).findByIdOrThrow(1L);
        verify(doctorRepository).findByIdOrThrow(2L);
        verify(medicalReportRepository).saveAndFlush(any(MedicalReport.class));
    }
    @Test
    void create_shouldCreateMedicalReportWithoutDoctor() {

        // Arrange
        CreateMedicalReportRequest request = new CreateMedicalReportRequest();
        request.setPatientId(1L);
        request.setReportType("LAB");
        request.setTitle("Blood Test");
        request.setDescription("Routine blood test");
        request.setFileUrl("https://example.com/report.pdf");
        request.setReportDate(LocalDateTime.of(2026, 1, 1, 10, 0));

        Patient patient = new Patient();
        patient.setId(1L);

        MedicalReport savedReport = new MedicalReport();
        savedReport.setId(101L);
        savedReport.setPatient(patient);
        savedReport.setDoctor(null);
        savedReport.setReportType(ReportType.LAB);
        savedReport.setTitle(request.getTitle());
        savedReport.setDescription(request.getDescription());
        savedReport.setFileUrl(request.getFileUrl());
        savedReport.setReportDate(request.getReportDate());

        when(patientRepository.findByIdOrThrow(1L)).thenReturn(patient);
        when(medicalReportRepository.saveAndFlush(any(MedicalReport.class)))
                .thenReturn(savedReport);

        // Act
        MedicalReportResponse response = medicalReportService.create(request);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(101L, response.getId());
        Assertions.assertEquals(1L, response.getPatientId());
        Assertions.assertNull(response.getDoctorId());
        Assertions.assertEquals("LAB", response.getReportType());

        verify(patientRepository).findByIdOrThrow(1L);
        verify(medicalReportRepository).saveAndFlush(any(MedicalReport.class));
    }

        @Test
        void create_shouldThrowIllegalArgumentExceptionForInvalidReportType() {

            // Arrange
            CreateMedicalReportRequest request = new CreateMedicalReportRequest();
            request.setPatientId(1L);
            request.setDoctorId(2L);
            request.setReportType("INVALID_TYPE");
            request.setTitle("Blood Test");
            request.setDescription("Routine blood test");
            request.setFileUrl("https://example.com/report.pdf");
            request.setReportDate(LocalDateTime.of(2026, 1, 1, 10, 0));

            Patient patient = new Patient();
            patient.setId(1L);

            Doctor doctor = new Doctor();
            doctor.setId(2L);

            when(patientRepository.findByIdOrThrow(1L)).thenReturn(patient);
            when(doctorRepository.findByIdOrThrow(2L)).thenReturn(doctor);

            // Act & Assert
            IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> medicalReportService.create(request)
            );

            Assertions.assertEquals(
                    "Invalid report type: INVALID_TYPE",
                    exception.getMessage()
            );

            verify(patientRepository).findByIdOrThrow(1L);
            verify(doctorRepository).findByIdOrThrow(2L);
        }
    }
