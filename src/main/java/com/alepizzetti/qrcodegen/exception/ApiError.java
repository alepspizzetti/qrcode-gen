package com.alepizzetti.qrcodegen.exception;

import java.time.Instant;
import java.util.List;

public class ApiError {
    private int status;
    private Instant timestamp;
    private List<FieldError> errors;

    public ApiError(int status, List<FieldError> errors) {
        this.status = status;
        this.timestamp = Instant.now();
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
