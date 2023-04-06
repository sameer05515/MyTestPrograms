package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NumberToWordUtil2Test {

    @Test
    void testNumberToWords() {
        assertEquals("One Thousand One Hundred Eleven", NumberToWordUtil2.numberToWords("1111"));
        assertEquals("Six Hundred Seventy Three", NumberToWordUtil2.numberToWords("673"));
        assertEquals("Eighty Five", NumberToWordUtil2.numberToWords("85"));
//        assertEquals("Five", NumberToWordUtil.numberToWords("5"));
//        assertEquals("Zero", NumberToWordUtil.numberToWords("0"));
//        assertEquals("Twenty", NumberToWordUtil.numberToWords("20"));
//        assertEquals("One Thousand", NumberToWordUtil.numberToWords("1000"));
//        assertEquals("Twelve Thousand Three Hundred Forty Five", NumberToWordUtil.numberToWords("12345"));
//        assertEquals("The string is empty.", NumberToWordUtil.numberToWords(""));
    }
}
