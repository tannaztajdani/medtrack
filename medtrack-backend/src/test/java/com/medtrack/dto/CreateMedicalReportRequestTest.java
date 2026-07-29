package com.medtrack.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

class CreateMedicalReportRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenRequiredFieldsAreInvalid() {

        CreateMedicalReportRequest request = new CreateMedicalReportRequest();

        request.setPatientId(null);
        request.setReportType("");
        request.setTitle("");
        request.setFileUrl("invalid-url");
        request.setReportDate(null);

        Set<ConstraintViolation<CreateMedicalReportRequest>> violations =
                validator.validate(request);

        Assertions.assertFalse(violations.isEmpty());

        Assertions.assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("patientId"))
        );

        Assertions.assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("reportType"))
        );

        Assertions.assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("title"))
        );

        Assertions.assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("fileUrl"))
        );

        Assertions.assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("reportDate"))
        );
    }

    @Test
    void shouldPassValidationWhenRequestIsValid() {

        CreateMedicalReportRequest request = new CreateMedicalReportRequest();

        request.setPatientId(1L);
        request.setDoctorId(2L);
        request.setReportType("LAB");
        request.setTitle("Blood Test");
        request.setDescription("Routine blood test");
        request.setFileUrl("https://example.com/report.pdf");
        request.setReportDate(LocalDateTime.now());

        Set<ConstraintViolation<CreateMedicalReportRequest>> violations =
                validator.validate(request);

        Assertions.assertTrue(violations.isEmpty());
    }
}