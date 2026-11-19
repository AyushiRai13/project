package com.example.student.exception;

public class DuplicateStudentIdException extends RuntimeException {
    public DuplicateStudentIdException(String message) {
        super(message);
    }
}
