package com.sumerge.careertrack.learnings_svc.exceptions;

public class LearningsException extends RuntimeException {
    public LearningsException() {
        super();
    }

    public LearningsException(String message) {
        super(message);
    }

    public LearningsException(String message, Object... args) {
        super(String.format(message, args));
    }
}