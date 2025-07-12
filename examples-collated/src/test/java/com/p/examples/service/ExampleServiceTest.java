package com.p.examples.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExampleServiceTest {
    private final ExampleService exampleService;

    @Autowired
    public ExampleServiceTest(ExampleService exampleService) {
        this.exampleService = exampleService;
    }
    @Test
    void testCheckPalindrome() {
        assertTrue(exampleService.checkPalindrome("radar"));
        assertFalse(exampleService.checkPalindrome("hello"));
    }
}
