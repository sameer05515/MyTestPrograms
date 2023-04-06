package com.p.examples.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
@SpringBootTest
class SubstringGeneratorTest {

    @Test
    void testNullString() {
        assertNull(SubstringGenerator.generatePossibleSubstrings(null));
    }

    @Test
    void testEmptyString() {
        assertNull(SubstringGenerator.generatePossibleSubstrings(""));
    }

    @Test
    void testSingleCharString() {
        assertArrayEquals(new String[]{"a"}, SubstringGenerator.generatePossibleSubstrings("a"));
    }

    @Test
    void testStringWithNoDuplicates() {
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, SubstringGenerator.generatePossibleSubstrings("abcd"));
    }

    @Test
    void testStringWithDuplicates() {
        assertArrayEquals(new String[]{"aa", "ab", "ac", "ad", "ba", "bb", "bc", "bd", "ca", "cb", "cc", "cd", "da", "db", "dc", "dd"},
                SubstringGenerator.generatePossibleSubstrings("abcd"));
    }
}
