package com.p.java.playground.api.sample;

import com.p.java.playground.api.base.response.ResponseMapper;
import com.p.java.playground.api.base.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * A sample controller to demonstrate the usage of ResponseMapper.
 */
@RestController
public class SampleController {

    @GetMapping("/success")
    public ResponseEntity<StandardResponse<int[]>> getSuccessResponse() {
        int[] data = {1, 2, 3, 4, 5};
        System.out.println("Request aaya!!: "+new Date());
        return ResponseMapper.createSuccessResponse(data, HttpStatus.OK);
    }

    @GetMapping("/error")
    public ResponseEntity<StandardResponse<String>> getErrorResponse() {
        return ResponseMapper.createErrorResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
