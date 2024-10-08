package com.sumerge.careertrack.learnings_svc.exceptions;

public class AlreadyExistsException extends LearningsException {

    public static final String LEVEL_NAME = "Scoreboard Level \"%s\" already exists.";
    public static final String LEVEL_SCORE = "Scoreboard Level with minimum score \"%d\" already exists.";
    public static final String SCORE = "A score for user \"%d\" already exists.";

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Object... args) {
        super(String.format(message, args));
    }
}
