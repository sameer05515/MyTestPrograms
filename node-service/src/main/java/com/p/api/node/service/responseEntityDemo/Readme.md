# Commonly used HTTP Status codes

Here’s a list of commonly used HTTP status codes grouped by category:

### **1xx: Informational**

- **100 Continue**: Indicates that the client should continue with its request.
- **101 Switching Protocols**: The server switches to the protocol requested by the client.

---

### **2xx: Success**

- **200 OK**: The request was successful.
- **201 Created**: The request was successful, and a resource was created.
- **202 Accepted**: The request has been accepted for processing but is not yet completed.
- **204 No Content**: The request was successful, but there is no content to return.

---

### **3xx: Redirection**

- **301 Moved Permanently**: The resource has been moved permanently to a new URL.
- **302 Found**: The resource is temporarily located at a different URL.
- **304 Not Modified**: The resource has not been modified; the client can use cached data.

---

### **4xx: Client Errors**

- **400 Bad Request**: The server could not understand the request due to invalid syntax.
- **401 Unauthorized**: Authentication is required and has failed or not been provided.
- **403 Forbidden**: The client does not have permission to access the resource.
- **404 Not Found**: The requested resource was not found on the server.
- **405 Method Not Allowed**: The HTTP method used is not allowed for the resource.
- **429 Too Many Requests**: The client has sent too many requests in a given time.

---

### **5xx: Server Errors**

- **500 Internal Server Error**: A generic server error occurred.
- **501 Not Implemented**: The server does not support the requested functionality.
- **502 Bad Gateway**: The server received an invalid response from an upstream server.
- **503 Service Unavailable**: The server is currently unavailable (e.g., due to maintenance).
- **504 Gateway Timeout**: The server did not receive a timely response from an upstream server.

These codes cover most common use cases in REST APIs, web applications, and general HTTP communication.

# please create a controller class to demonstrate use of various methods of org.springframework.http.ResponseEntity

Here is a demonstration of a Spring Boot controller showcasing various methods of
`org.springframework.http.ResponseEntity`:

### Controller Class: `ResponseEntityDemoController`

```java
package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/response-demo")
public class ResponseEntityDemoController {

    // Example 1: Returning a plain OK response
    @GetMapping("/ok")
    public ResponseEntity<String> getOkResponse() {
        return ResponseEntity.ok("This is an OK response");
    }

    // Example 2: Returning a response with a custom status and body
    @GetMapping("/custom-status")
    public ResponseEntity<String> getCustomStatus() {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Request was accepted for processing");
    }

    // Example 3: Adding headers to the response
    @GetMapping("/headers")
    public ResponseEntity<String> getResponseWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomHeaderValue");
        return ResponseEntity.ok()
                .headers(headers)
                .body("Response with custom headers");
    }

    // Example 4: Returning a response with no content
    @DeleteMapping("/no-content")
    public ResponseEntity<Void> deleteResource() {
        return ResponseEntity.noContent().build();
    }

    // Example 5: Using created() to return a location header for a newly created resource
    @PostMapping("/created")
    public ResponseEntity<String> createResource() {
        URI location = URI.create("/response-demo/resource/123");
        return ResponseEntity.created(location).body("Resource created with ID 123");
    }

    // Example 6: Returning a bad request response
    @GetMapping("/bad-request")
    public ResponseEntity<String> getBadRequest() {
        return ResponseEntity.badRequest().body("Invalid request");
    }

    // Example 7: Building a response with custom headers and status
    @GetMapping("/custom-response")
    public ResponseEntity<Map<String, String>> getCustomResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "ExampleValue");

        Map<String, String> body = new HashMap<>();
        body.put("message", "Custom response body");
        body.put("status", "success");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(body);
    }

    // Example 8: Handling a NOT FOUND response
    @GetMapping("/not-found")
    public ResponseEntity<String> getNotFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
    }
}
```

### Key Methods Used

1. **`ResponseEntity.ok()`**: Sends an HTTP 200 OK response with a body.
2. **`ResponseEntity.status(HttpStatus)`**: Sets a custom HTTP status code.
3. **`ResponseEntity.headers(HttpHeaders)`**: Adds custom headers to the response.
4. **`ResponseEntity.noContent()`**: Sends an HTTP 204 No Content response.
5. **`ResponseEntity.created(URI)`**: Sends an HTTP 201 Created response with a `Location` header.
6. **`ResponseEntity.badRequest()`**: Sends an HTTP 400 Bad Request response.
7. **`ResponseEntity.build()`**: Builds a response without a body.
8. **Custom Headers and Body**: Combines headers, body, and status.

### How to Test

Each method corresponds to a different endpoint. You can test them using `curl`, Postman, or a browser. For example:

- **GET `/response-demo/ok`** → Returns a 200 OK with a plain message.
- **GET `/response-demo/headers`** → Returns a 200 OK with custom headers.
- **POST `/response-demo/created`** → Returns a 201 Created with a `Location` header.

Let me know if you'd like to expand on any part!

# Reference

- [**HTTP response status codes**](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)