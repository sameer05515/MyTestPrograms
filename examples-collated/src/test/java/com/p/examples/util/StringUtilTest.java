package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StringUtilTest {

    @Test
    void testCountCharacterOccurrences() {
        String str = "Geeksajkakakakakkaldlmjhbfhuvhbvcjnjvnljfvnn";
        Map<Character, Integer> charCountMap = StringUtil.countCharacterOccurrences(str);
        assertEquals(16, charCountMap.size());
        assertEquals(5, charCountMap.get('a'));
        assertEquals(1, charCountMap.get('G'));
        assertEquals(0, charCountMap.getOrDefault('z', 0));
    }

    @Test
    void testReverseString() {
        String str = "Geeks";
        String reversedStr = StringUtil.reverseString(str);
        assertEquals("skeeG", reversedStr);
    }

    @Test
    void testIsPalindrome() {


        assertTrue(StringUtil.isPalindrome("bannab"), "Hurrey");
        assertFalse(StringUtil.isPalindrome("Geeks"), "Hurrey");
    }

    @Test
    void test_findFirstNonRepeatedChar() {
        assertEquals('w', StringUtil.findFirstNonRepeatedChar("swiss"));
        assertEquals('\0', StringUtil.findFirstNonRepeatedChar("bannab"));
    }

    @Test
    void test_removeWhiteSpaces(){
        assertEquals("Thisisateststring", StringUtil.removeWhiteSpaces("This is a test string"));
    }

    @Test
    void test_areAnagrams(){
        assertTrue(StringUtil.areAnagrams("listen", "silent"));
        assertFalse(StringUtil.areAnagrams("abc", "aabbcc"));
    }

    @Test
    void test_findMaxOccurringChar(){
        //The maximum occurring character is: s
        assertEquals('s', StringUtil.findMaxOccurringChar("sample string"));
    }

    @Test
    void test_toSnakeCase(){
        String input = "Convert This String to Snake Case";
        assertEquals("convert_this_string_to_snake_case", StringUtil.toSnakeCase1(input));
        assertEquals("convert_this_string_to_snake_case", StringUtil.toSnakeCase2(input));
    }
}
