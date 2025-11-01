// package com.p.node.service.base.exception;
//
// import com.p.node.service.base.response.ResponseMapper;
// import com.p.node.service.base.response.StandardResponse;
// import java.util.NoSuchElementException;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
//
/// **
// * The GlobalExceptionHandler class provides a global exception handling mechanism to ensure
// * consistent error responses across the application.
// */
// @RestControllerAdvice
// public class GlobalExceptionHandler {
//
//  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//  /**
//   * Handles IllegalArgumentException and returns an appropriate response.
//   *
//   * @param ex The exception.
//   * @return A StandardResponse containing the error message with HTTP 400 (Bad Request) status.
//   */
//  @ExceptionHandler(IllegalArgumentException.class)
//  public ResponseEntity<StandardResponse<Object>> handleIllegalArgumentException(
//      IllegalArgumentException ex) {
//
//    logger.error("IllegalArgumentException occurred: {}", ex.getMessage(), ex);
//    return ResponseMapper.createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
//  }
//
//  /**
//   * Handles NoSuchElementException and returns an appropriate response.
//   *
//   * @param ex The exception.
//   * @return A StandardResponse containing the error message with HTTP 404 (Not Found) status.
//   */
//  @ExceptionHandler(NoSuchElementException.class)
//  public ResponseEntity<StandardResponse<Object>> handleNoSuchElementException(
//      NoSuchElementException ex) {
//
//    logger.warn("NoSuchElementException occurred: {}", ex.getMessage(), ex);
//    return ResponseMapper.createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
//  }
//
//  /**
//   * Generic exception handler for all other exceptions not explicitly handled.
//   *
//   * @param ex The exception.
//   * @return A ResponseEntity containing a standardized error response with HTTP 500 (Internal
//   *     Server Error) status.
//   */
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<StandardResponse<Object>> handleGenericException(Exception ex) {
//
//    logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
//    return ResponseMapper.createErrorResponse(
//        "An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex);
//  }
// }
