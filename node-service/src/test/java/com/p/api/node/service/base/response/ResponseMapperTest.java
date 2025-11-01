package com.p.api.node.service.base.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ResponseMapperTest {

  @Test
  void testCreateSuccessResponseWithData() {
    String data = "Sample Data";
    HttpStatus status = HttpStatus.OK;

    ResponseEntity<StandardResponse<String>> response =
        ResponseMapper.createSuccessResponse(data, status);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("success", response.getBody().getStatus());
    assertEquals("Request was successful", response.getBody().getMessage());
    assertEquals("Sample Data", response.getBody().getData());
    assertEquals(HttpStatus.OK.value(), response.getBody().getStatusCode());
    assertNotNull(response.getBody().getTimeStamp());
  }

  @Test
  void testCreateSuccessResponseWithMessageAndData() {
    String message = "Custom Success Message";
    String data = "Sample Data";
    HttpStatus status = HttpStatus.CREATED;

    ResponseEntity<StandardResponse<String>> response =
        ResponseMapper.createSuccessResponse(message, data, status);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("success", response.getBody().getStatus());
    assertEquals("Request was successful: Custom Success Message", response.getBody().getMessage());
    assertEquals("Sample Data", response.getBody().getData());
    assertEquals(HttpStatus.CREATED.value(), response.getBody().getStatusCode());
    assertNotNull(response.getBody().getTimeStamp());
  }

  @Test
  void testCreateErrorResponseWithMessage() {
    String message = "Error occurred";
    HttpStatus status = HttpStatus.BAD_REQUEST;

    ResponseEntity<StandardResponse<Object>> response =
        ResponseMapper.createErrorResponse(message, status);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("Error occurred", response.getBody().getMessage());
    assertNull(response.getBody().getData());
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
    assertNotNull(response.getBody().getTimeStamp());
  }

  @Test
  void testCreateErrorResponseWithException() {
    String message = "Error occurred";
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    Exception exception = new RuntimeException("Runtime Exception Details");

    ResponseEntity<StandardResponse<Object>> response =
        ResponseMapper.createErrorResponse(message, status, exception);

    assertNotNull(response);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("error", response.getBody().getStatus());
    assertEquals("Error occurred: Runtime Exception Details", response.getBody().getMessage());
    assertNull(response.getBody().getData());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatusCode());
    assertNotNull(response.getBody().getTimeStamp());
  }
}
