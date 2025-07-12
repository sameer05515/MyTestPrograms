package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class StringAdderTest {

    private final StringAdder stringAdder;

    @Autowired
    public StringAdderTest(StringAdder stringAdder) {
        this.stringAdder = stringAdder;
    }
    @Test
    void testAdd() {
        assertEquals("10", stringAdder.add("10", ""));
        assertEquals("10", stringAdder.add("", "10"));
        assertEquals("111", stringAdder.add("99", "12"));
        assertEquals("1000", stringAdder.add("500", "500"));
        assertEquals("101", stringAdder.add("100", "1"));
    }
}
