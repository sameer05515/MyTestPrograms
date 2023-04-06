package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NumberToWordsUtilTest {

    @Test
    void testToWords() {
        assertEquals("One Thousand Two Hundred Thirty Four", NumberToWordsUtil.toWords("1234"));
        assertEquals("Six Hundred Seventy Three", NumberToWordsUtil.toWords("673"));
        assertEquals("Eighty Five", NumberToWordsUtil.toWords("85"));
        assertEquals("Five", NumberToWordsUtil.toWords("5"));
        assertEquals("Zero", NumberToWordsUtil.toWords("0"));
        assertEquals("Twenty", NumberToWordsUtil.toWords("20"));
        assertEquals("One Thousand", NumberToWordsUtil.toWords("1000"));
        assertEquals("Twelve Thousand Three Hundred Forty Five", NumberToWordsUtil.toWords("12345"));
    }

    @Test
    void testToWordsWithInvalidInput() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> NumberToWordsUtil.toWords("123a"));
        assertEquals("Input must be a non-negative integer.", exception.getMessage());
    }
}
