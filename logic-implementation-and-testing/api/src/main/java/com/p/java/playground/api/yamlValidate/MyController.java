package com.p.java.playground.api.yamlValidate;

import com.p.java.playground.api.base.response.ResponseMapper;
import com.p.java.playground.api.base.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private MyService myService;

    @PostMapping("/validateYaml")
    public ResponseEntity<StandardResponse<MyPojo>> validateYaml(@RequestBody String yamlInput) throws Exception {
//        try {
            MyPojo pojo = myService.parseAndValidateYaml(yamlInput);
//            return ResponseEntity.ok("YAML is valid and parsed: " + pojo.getName());
            return ResponseMapper.createSuccessResponse(pojo, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
    }
}
