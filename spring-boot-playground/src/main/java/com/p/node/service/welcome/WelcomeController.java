package com.p.node.service.welcome;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {

  @GetMapping
  public ResponseEntity<String> welcome() {
    //    return ResponseMapper.createSuccessResponse("Welcome", "", HttpStatus.OK);
    return ResponseEntity.ok("welcome");
  }
}
