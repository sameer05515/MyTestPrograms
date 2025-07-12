package com.p.examples.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringUtil {

    public static Map<Character, Integer> countCharacterOccurrences(String str) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        for (char c : str.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        return charCountMap;
    }

    /**
     * a method to reverse a given string without using the built-in reverse() function
     */
    public static String reverseString(String str) {
        StringBuilder reversedString = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            reversedString.append(str.charAt(i));
        }
        return reversedString.toString();
    }

    /**
     * Check if a given string is a palindrome (reads the same backward as forward)
     */
    public static boolean isPalindrome(String str) {
        boolean result = false;
        if (str != null && str.trim().length() > 0) {
            int strLength = str.trim().length();
            int itr = 0;
            while (itr <= strLength / 2) {
                if (str.charAt(itr) != str.charAt(strLength - itr - 1)) {
                    result = false;
                    break;
                }
                result = true;
                itr++;
            }
        }
        return result;
    }

    /**
     * Find the first non-repeated character in a given string
     */
    public static char findFirstNonRepeatedChar(String str) {
        Map<Character, Integer> charCountMap = new LinkedHashMap<>();

        // Populate the character count map
        for (char c : str.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        // Find the first non-repeated character
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        // Return a default value if no non-repeated character is found
        return '\0';
    }

    /**
     * a method to remove all white spaces from a given string
     */
    public static String removeWhiteSpaces(String str) {
        return str.replaceAll("\\s", "");
    }

    /**
     * Check if two given strings are anagrams of each other (contain the same characters in a different order)
     * <p>
     * anagrams require not only the same set of characters but also the same number of each character.
     * <p>
     * Here is a simple counterexample:
     * <p>
     * str1 = "aabbcc"
     * str2 = "abc"
     * Both strings have the same distinct characters {'a', 'b', 'c'}, but they are clearly not anagrams because str1 contains more 'a's, 'b's, and 'c's than str2.
     * <p>
     * The correct approach should involve counting the frequency of each character in both strings and ensuring these frequencies match. Here’s an implementation using HashMap for better clarity:
     */

    public static boolean areAnagrams(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }

        Map<Character, Integer> charCountMap1 = getCharCountMap(str1);
        Map<Character, Integer> charCountMap2 = getCharCountMap(str2);

        return charCountMap1.equals(charCountMap2);
    }
    private static Map<Character, Integer> getCharCountMap(String str) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        for (char c : str.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }
        return charCountMap;
    }

    /**
     * Find the maximum occurring character in a given string.
     * */
    public static char findMaxOccurringChar(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        Map<Character, Integer> charCountMap = new HashMap<>();

        // Populate the character count map
        for (char c : str.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        // Find the character with the maximum count
        char maxChar = '\0';
        int maxCount = 0;
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxChar = entry.getKey();
            }
        }

        return maxChar;
    }

    /**
     * Write a method to convert a given string to snake case (lowercase with underscores separating words)
     * */
    /**
     * Converts a given string to snake case (lowercase with underscores separating words).
     *
     * <p>A string is considered to be in snake case if:
     * <ul>
     * <li>It consists entirely of lowercase letters (a-z).</li>
     * <li>It may contain underscores (_) to separate words or parts of words.</li>
     * <li>There should be no spaces in the string.</li>
     * <li>It may contain alphanumeric characters (a-z, 0-9).</li>
     * <li>It should not start or end with an underscore.</li>
     * </ul>
     *
     * @param str the input string to convert to snake case
     * @return the input string converted to snake case
     */
    public static String toSnakeCase1(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Replace all non-alphanumeric characters (except underscores) with underscores
        String snakeCase = str.replaceAll("[^a-zA-Z0-9_]+", "_");

        // Remove leading and trailing underscores
        snakeCase = snakeCase.replaceAll("^_+|_+$", "");

        // Convert to lowercase
        return snakeCase.toLowerCase();
    }

    public static String toSnakeCase2(String input) {
        if (input == null || input.isEmpty()) {
            return ""; // or throw an IllegalArgumentException
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                result.append('_');
                first = true;
            } else if (Character.isUpperCase(c)) {
                if (!first) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
                first = false;
            } else {
                result.append(c);
                first = false;
            }
        }

        return result.toString();
    }

    /**
     * Checks if a given string contains only digits (0-9).
     *
     * @param str the input string to check
     * @return true if the string contains only digits, false otherwise
     */
    public static boolean containsOnlyDigits(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        // Check each character in the string
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a given string contains only digits (0-9).
     *
     * @param str the input string to check
     * @return true if the string contains only digits, false otherwise
     */
    public static boolean containsOnlyDigits2(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        // Use regex to check if the string contains only digits
        return str.matches("\\d+");
    }

    public static void main(String[] args) {
        String input = "123";
        boolean res = containsOnlyDigits(input);
        System.out.println("containsOnlyDigits " + res); // Output: convert_this_string_to_snake_case
    }


}
