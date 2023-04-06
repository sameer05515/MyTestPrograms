package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class DataTypeFitterTest {

    private final DataTypeFitter dataTypeFitter;

    @Autowired
    public DataTypeFitterTest(DataTypeFitter dataTypeFitter) {
        this.dataTypeFitter = dataTypeFitter;
    }


    @Test
    void testPrintFittedTypes() {
        // Redirect standard output to capture the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Call the method to print fitted types
        dataTypeFitter.printFittedTypes(0L);

        // Restore standard output
        System.setOut(originalOut);

        // Get the printed output as a string
        String printedOutput = outputStream.toString().trim();

        // Define the expected output
        String expectedOutput = "* byte\n* short\n* int\n* long";

        // Assert that the printed output matches the expected output
        assertEquals(expectedOutput, printedOutput);
    }
}
