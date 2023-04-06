package com.p.examples.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

@Component
public class NumberToWordsUtil {

    private static final String[] ONES = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] POWERS_OF_TEN = {"", "Hundred", "Thousand", "Lakh", "Crore", "Arab", "Kharab", "Padma",
            "Shankh", "Samudra", "Antya", "Madhym", "Paraardha"};

    public static String toWords(String number) {
        if (!number.matches("\\d+")) {
            throw new IllegalArgumentException("Input must be a non-negative integer.");
        }

        List<String> splitNumber = splitNumber(number);
        Collections.reverse(splitNumber);

        StringJoiner joiner = new StringJoiner(" ");
        for (int i = 0; i < splitNumber.size(); i++) {
            String part = splitNumber.get(i);
            int intValue = Integer.parseInt(part);
            if (intValue > 0) {
                joiner.add(convertThreeDigitToWord(part) + " " + POWERS_OF_TEN[i]);
            }
        }

        return joiner.toString().trim();
    }

    private static List<String> splitNumber(String number) {
        List<String> parts = new ArrayList<>();
        for (int i = number.length(); i > 0; i -= 3) {
            int start = Math.max(i - 3, 0);
            parts.add(number.substring(start, i));
        }
        return parts;
    }

    private static String convertThreeDigitToWord(String number) {
        StringBuilder word = new StringBuilder();
        int num = Integer.parseInt(number);
        if (num >= 100) {
            word.append(ONES[num / 100]).append(" Hundred ");
            num %= 100;
        }
        if (num > 0) {
            if (word.length() > 0) {
                word.append("and ");
            }
            if (num < 20) {
                word.append(ONES[num]);
            } else {
                word.append(TENS[num / 10]).append(" ").append(ONES[num % 10]);
            }
        }
        return word.toString().trim();
    }
}
