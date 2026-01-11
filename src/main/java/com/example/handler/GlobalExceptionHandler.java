package com.example.handler;

import com.example.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EntityModel<ErrorResponse>> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path("/api/v1/users")
                .build();

        EntityModel<ErrorResponse> resource = EntityModel.of(error);
        resource.add(linkTo(methodOn(com.example.controller.UserController.class).getAllUsers()).withRel("users"));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resource);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EntityModel<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message("Validation failed for arguments")
                .path("/api/v1/users")
                .build();

        EntityModel<ErrorResponse> resource = EntityModel.of(error);
        resource.add(linkTo(methodOn(com.example.controller.UserController.class).getApiRoot()).withRel("api-root"));

        return ResponseEntity.badRequest().body(resource);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<EntityModel<ErrorResponse>> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Constraint Violation")
                .message(ex.getMessage())
                .path("/api/v1/users")
                .build();

        return ResponseEntity.badRequest().body(EntityModel.of(error));
    }
}
