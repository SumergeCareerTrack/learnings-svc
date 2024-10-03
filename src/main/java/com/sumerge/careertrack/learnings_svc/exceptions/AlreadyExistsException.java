package com.sumerge.careertrack.learnings_svc.exceptions;

public class AlreadyExistsException extends LearningsException {

    public static final String LEVEL_NAME = "Scoreboard Level \"%s\" already exists.";
    public static final String LEVEL_SCORE = "Scoreboard Level with minimum score \"%d\" already exists.";
    public static final String LEARNING_TYPE = "Learning Type \"%s\" already exists.";
    public static final String LEARNING_SUBJECT = "Learning Subject \"%s\" already exists.";
    public static final String LEARNING_HAS_SUBJECT = "\"%s\" Learnings with subject \"%s\" already exists.";
    public static final String LEARNING_HAS_TYPE = "\"%s\" Learnings  with type \"%s\" already exists.";

    public static final String BOOSTER = "Booster Type \"%s\" already exists.";
    public static final String BOOSTER_TYPE = "Booster Type \"%s\" already exists.";


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

