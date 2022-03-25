package br.com.fatec.server.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.fatec.server.exceptions.ResourceAlreadyExistsException;
import br.com.fatec.server.exceptions.ResourceNotFoundException;
import br.com.fatec.server.responses.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;


@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity(error, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity(error, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public final ResponseEntity<Object> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity(error, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity(error, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<Object> handleExpiredJwtException(
            ExpiredJwtException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Your token has expired", HttpStatus.NOT_FOUND);
        return new ResponseEntity(error, HttpStatus.valueOf(error.getStatusCode()));
    }

}