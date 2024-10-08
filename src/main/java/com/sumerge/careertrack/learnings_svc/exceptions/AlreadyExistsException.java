package com.sumerge.careertrack.learnings_svc.exceptions;

public class AlreadyExistsException extends LearningsException {

    public static final String LEVEL_NAME = "Scoreboard Level \"%s\" already exists.";
    public static final String LEVEL_SCORE = "Scoreboard Level with minimum score \"%d\" already exists.";
    public static final String SCORE = "A score for user \"%d\" already exists.";
    public static final String LEARNING_TYPE = "Learning Type \"%s\" already exists.";
    public static final String LEARNING_SUBJECT = "Learning Subject \"%s\" already exists.";
    public static final String LEARNING_HAS_SUBJECT = "Learnings with subject \"%s\" already exists.";
    public static final String LEARNING_HAS_TYPE = "Learnings  with type \"%s\" already exists.";
    public static final String MULTIPLE_LEARNINGS = "Learnings with same type,subject,url and description already exists.";

    public static final String BOOSTER = "Booster Type \"%s\" already exists.";
    public static final String BOOSTER_TYPE = "Booster Type \"%s\" already exists.";
    public static final String PROOF_TYPE = "Proof Type \"%s\" already exists.";

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