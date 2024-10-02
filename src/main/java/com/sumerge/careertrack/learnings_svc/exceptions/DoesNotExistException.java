package com.sumerge.careertrack.learnings_svc.exceptions;

public class DoesNotExistException extends LearningsException {

    public static final String LEVEL = "Scoreboard Level \"%s\" does not exist.";
    public static final String BOOSTER_TYPE = "Booster Type \"%s\" does not exist.";

    public DoesNotExistException() {
        super();
    }

    public DoesNotExistException(String message) {
        super(message);
    }

    public DoesNotExistException(String message, Object... args) {
        super(String.format(message, args));
    }
}
