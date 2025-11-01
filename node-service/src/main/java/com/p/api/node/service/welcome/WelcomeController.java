package com.p.api.node.service.welcome;

import com.p.api.node.service.base.response.ResponseMapper;
import com.p.api.node.service.base.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StandardResponse<String>> welcome() {
    return ResponseMapper.createSuccessResponse("Welcome", "", HttpStatus.OK);
  }
}
