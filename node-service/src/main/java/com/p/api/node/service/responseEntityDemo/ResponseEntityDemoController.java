package com.p.api.node.service.responseEntityDemo;

import com.p.api.node.service.base.util.HttpStatusUtil;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request was accepted for processing");
  }

  // Example 3: Adding headers to the response
  @GetMapping("/headers")
  public ResponseEntity<String> getResponseWithHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Custom-Header", "CustomHeaderValue");
    return ResponseEntity.ok().headers(headers).body("Response with custom headers");
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

    return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(body);
  }

  // Example 8: Handling a NOT FOUND response
  @GetMapping("/not-found")
  public ResponseEntity<String> getNotFoundResponse() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
  }

  @GetMapping("getHttpStatusList")
  public ResponseEntity<List<HttpStatus>> getHttpStatusList() {
    return ResponseEntity.ok(HttpStatusUtil.getHttpStatusList());
  }

  @GetMapping("getHttpStatusValues")
  public ResponseEntity<List<String>> getHttpStatusValues() {
    return ResponseEntity.ok(HttpStatusUtil.getHttpStatusValues());
  }

  @GetMapping("/response-acc-to-status-code/{code}")
  public ResponseEntity<String> getResponseForCode(@PathVariable int code) {
    return ResponseEntity.status(code).body("Request processed for code: " + code);
  }
}
