package com.example.techcorp.exception;

public class ProjectAlreadyFinishedException extends RuntimeException {

    public ProjectAlreadyFinishedException(String message) {
        super(message);
    }
}