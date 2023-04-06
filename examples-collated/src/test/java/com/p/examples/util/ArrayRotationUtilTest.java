package com.p.examples.util;

import com.p.examples.service.ExampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
public class ArrayRotationUtilTest {

    private final ArrayRotationUtil exampleService;

    @Autowired
    public ArrayRotationUtilTest(ArrayRotationUtil exampleService) {
        this.exampleService = exampleService;
    }
  @Test
    void testArrayLeftRotation() {
        int[] inputArray = {1, 2, 3, 4, 5};
        int[] expectedArray = {3, 4, 5, 1, 2};
        assertArrayEquals(expectedArray, exampleService.arrayLeftRotation(inputArray, 5, 2));

        // Test case with rotation greater than array length
//        int[] inputArray2 = {1, 2, 3, 4, 5};
//        int[] expectedArray2 = {4, 5, 1, 2, 3};
//        assertArrayEquals(expectedArray2, exampleService.arrayLeftRotation(inputArray2, 5, 7));
    }
}
