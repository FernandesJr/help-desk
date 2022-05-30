package com.gilfer.helpdesk.resources.exceptions;

import com.gilfer.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.gilfer.helpdesk.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandarError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandarError error = new StandarError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    //DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandarError> resourceNotFound(DataIntegrityViolationException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandarError error = new StandarError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Integridade violada");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
