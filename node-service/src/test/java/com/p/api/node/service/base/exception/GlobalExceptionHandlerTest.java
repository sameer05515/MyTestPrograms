package com.p.api.node.service.base.exception;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class GlobalExceptionHandlerTest {

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new TestController()) // Use a mock controller
            .setControllerAdvice(new GlobalExceptionHandler())
            .alwaysDo(
                result -> result.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE))
            .build();
  }

  //  @Test
  //  void testHandleIllegalArgumentException() throws Exception {
  //    mockMvc
  //        .perform(get("/api/test/illegal-argument"))
  //        .andExpect(status().isBadRequest())
  //        .andExpect(jsonPath("$.status").value("error"))
  //        .andExpect(jsonPath("$.message").value("Invalid argument provided"));
  //  }
  //
  //  @Test
  //  void testHandleNoSuchElementException() throws Exception {
  //    mockMvc
  //        .perform(get("/api/test/no-such-element"))
  //        .andExpect(status().isNotFound())
  //        .andExpect(jsonPath("$.status").value("error"))
  //        .andExpect(jsonPath("$.message").value("No such element found"));
  //  }
  //
  //  @Test
  //  void testHandleGenericException() throws Exception {
  //    mockMvc
  //        .perform(get("/api/test/generic-error"))
  //        .andExpect(status().isInternalServerError())
  //        .andExpect(jsonPath("$.status").value("error"))
  //        .andExpect(
  //            jsonPath("$.message")
  //                .value("An unexpected error occurred: Generic exception occurred"));
  //  }
}

@RestController
class TestController {

  @GetMapping("/api/test/illegal-argument")
  public void triggerIllegalArgumentException() {
    throw new IllegalArgumentException("Invalid argument provided");
  }

  @GetMapping("/api/test/no-such-element")
  public void triggerNoSuchElementException() {
    throw new NoSuchElementException("No such element found");
  }

  @GetMapping("/api/test/generic-error")
  public void triggerGenericException() {
    throw new RuntimeException("Generic exception occurred");
  }
}
