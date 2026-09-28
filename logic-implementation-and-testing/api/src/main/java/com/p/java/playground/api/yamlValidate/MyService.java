package com.p.java.playground.api.yamlValidate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

@Service
public class MyService {

    private final Validator validator;

    public MyService() {
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            this.validator = factory.getValidator();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create Validator", e);
        }
    }

    public MyPojo parseAndValidateYaml(String yamlInput) throws Exception {
        // Parse the YAML into MyPojo
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        MyPojo pojo = objectMapper.readValue(yamlInput, MyPojo.class);

        // Validate the POJO
        Set<ConstraintViolation<MyPojo>> violations = validator.validate(pojo);
        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder();
            for (ConstraintViolation<MyPojo> violation : violations) {
                errors.append(violation.getMessage()).append("; ");
            }
            throw new Exception("Validation failed: " + errors.toString());
        }

        // If valid, return the POJO
        return pojo;
    }
}
