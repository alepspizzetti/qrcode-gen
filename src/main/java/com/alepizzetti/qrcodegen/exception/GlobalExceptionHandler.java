package com.alepizzetti.qrcodegen.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ApiError.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiError.FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(400, fieldErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMalformedJson(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException cause) {
            String field = cause.getPath().stream()
                    .map(ref -> ref.getFieldName())
                    .findFirst()
                    .orElse("json");

            String message = "Valore non valido per il campo '" + field + "'.";
            return ResponseEntity.badRequest().body(
                    new ApiError(400, List.of(new ApiError.FieldError(field, message)))
            );
        }

        return ResponseEntity.badRequest().body(
                new ApiError(400, List.of(new ApiError.FieldError("json", "Richiesta malformata o campi con tipo non valido.")))
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessError(BusinessException ex) {
        ApiError apiError = new ApiError(400, List.of(
                new ApiError.FieldError("negozio", ex.getMessage())
        ));
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericError(Exception ex) {
        log.error("Errore inaspettato: ", ex);
        ApiError apiError = new ApiError(500, List.of(
                new ApiError.FieldError("error", "Errore interno inaspettato.")
        ));
        return ResponseEntity.internalServerError().body(apiError);
    }
}