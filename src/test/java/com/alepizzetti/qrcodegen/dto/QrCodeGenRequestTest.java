package com.alepizzetti.qrcodegen.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QrCodeGenRequestTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarDtoValido() {
        QrCodeGenRequest dto = new QrCodeGenRequest("https://test.com", 200, 200);
        Set<ConstraintViolation<QrCodeGenRequest>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deve haver violações para um DTO válido");
    }

    @Test
    void deveDetectarLinkVazio() {
        QrCodeGenRequest dto = new QrCodeGenRequest("", 200, 200);
        Set<ConstraintViolation<QrCodeGenRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("link")));
    }

    @Test
    void deveAceitarWidthENull() {
        QrCodeGenRequest dto = new QrCodeGenRequest("https://test.com", null, null);
        Set<ConstraintViolation<QrCodeGenRequest>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Se width e height forem null, não deve haver erro (serão tratados como default depois)");
    }

    @Test
    void deveDetectarWidthForaDoLimite() {
        QrCodeGenRequest dto = new QrCodeGenRequest("https://test.com", 50, 2000);
        Set<ConstraintViolation<QrCodeGenRequest>> violations = validator.validate(dto);

        assertEquals(2, violations.size(), "Width < 100 e height > 1000 devem ser inválidos");
    }
}