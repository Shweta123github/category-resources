package com.productmanagement.categoryresources.exception;

import com.productmanagement.categoryresources.constants.ProductManagementErrorMessages;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
        @ExceptionHandler(ResourceNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ResponseBody
        public ResponseEntity<ProductManagementErrorMessages> handleResourceNotFoundException(ResourceNotFoundException ex) {
            log.error("Resource not found: {}", ex.getMessage());
            return new ResponseEntity<>(ProductManagementErrorMessages.BAD_REQUEST, HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<List<ProductManagementErrorMessages>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation exception: {}", ex.getMessage());
        List<ProductManagementErrorMessages> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(ProductManagementErrorMessages.BAD_REQUEST);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ProductManagementErrorMessages> handleBindExceptions(BindException ex) {
        log.error("Bind exception: {}", ex.getMessage());
        String errorMessage = ex.getFieldError().getDefaultMessage();
        return new ResponseEntity<>(ProductManagementErrorMessages.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ProductManagementErrorMessages> handleAllExceptions(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage());
        return new ResponseEntity<>(ProductManagementErrorMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}










