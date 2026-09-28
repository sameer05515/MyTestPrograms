package com.p.problem.mgmt.nosql.controller;

import com.p.problem.mgmt.nosql.exception.BusinessDataNotFoundException;
import com.p.problem.mgmt.nosql.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControlAdvice {

  @ExceptionHandler(CustomValidationException.class)
  public ResponseEntity<String> handleCustomValidation(CustomValidationException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BusinessDataNotFoundException.class)
  public ResponseEntity<String> handleProblemNotFound(BusinessDataNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
