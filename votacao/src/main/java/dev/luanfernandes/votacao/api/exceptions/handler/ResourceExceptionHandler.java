package dev.luanfernandes.votacao.api.exceptions.handler;

import dev.luanfernandes.votacao.api.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.rmi.ServerException;
import java.time.Instant;

@Slf4j
@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String NOT_FOUND = "Not found";
    private static final String BAD_REQUEST = "Bad request";
    private static final String CONFLICT = "Conflict";
    private static final String UNEXPECTED_ERROR = "Unexpected error";
    private static final String NULL_POINTER = "Null Pointer";
    private static final String DATABASE_ERROR = "Database error";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> notFoundException(HttpServletRequest req, NotFoundException e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(),
                NOT_FOUND, e.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<StandardError> externalApiException(HttpServletRequest req, ExternalApiException e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST, e.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<StandardError> validationException(HttpServletRequest req, ValidationException e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST, e.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StandardError> conflictException(HttpServletRequest req, ConflictException e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.CONFLICT.value(),
                CONFLICT, e.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFound(HttpServletRequest req, EntityNotFoundException e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.NOT_FOUND.value(),
                NOT_FOUND, e.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardError> nullPointerException(HttpServletRequest req, NullPointerException e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.BAD_REQUEST.value(),
                NULL_POINTER, e.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<StandardError> handleDataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest req) {
        if (e.getCause() == null) {
            return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    UNEXPECTED_ERROR, e.getMessage(), req.getRequestURI()));
        }
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.CONFLICT.value(),
                DATABASE_ERROR, e.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> defaultErrorHandler(HttpServletRequest req, Exception e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                UNEXPECTED_ERROR, e.getMessage(), req.getRequestURI()));
    }
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<StandardError> serverException(HttpServletRequest req, ServerException e) {
        return buildResponseEntity(new StandardError(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), req.getRequestURI()));
    }

    private ResponseEntity<StandardError> buildResponseEntity(StandardError error) {
        log.error("{}",error);
        return ResponseEntity.status(HttpStatus.valueOf(error.getStatus())).body(error);
    }
}