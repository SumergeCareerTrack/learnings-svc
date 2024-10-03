package com.sumerge.careertrack.learnings_svc.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // TODO we need to make returned STATUS CODES be clear CONFLICT & NOTFOUND
    @ExceptionHandler({ DoesNotExistException.class })
    public ResponseEntity<Object> handleDoesNotExistException(DoesNotExistException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }
}