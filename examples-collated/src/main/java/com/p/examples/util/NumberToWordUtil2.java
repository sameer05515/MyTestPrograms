package com.p.examples.util;

import org.springframework.stereotype.Component;

@Component
public class NumberToWordUtil2 {

    private static final String[] ONES = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    private static final String[] TEENS = {"", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] POWERS_OF_TEN = {"Hundred", "Thousand"};

    public static String numberToWords(String num) {
        if (num == null || num.isEmpty()) {
            return "The string is empty.";
        }

        char[] numArr = num.toCharArray();
        int len = numArr.length;
        StringBuilder result = new StringBuilder();

        int x = 0;
        while (x < numArr.length) {
            if (len >= 3) {
                if (numArr[x] - '0' != 0) {
                    result.append(ONES[numArr[x] - '0']).append(" ").append(POWERS_OF_TEN[len - 3]).append(" ");
                }
                --len;
            } else {
                if (numArr[x] - '0' == 1) {
                    int sum = numArr[x] - '0' + numArr[x + 1] - '0';
                    result.append(TEENS[sum]);
                    return result.toString();
                } else if (numArr[x] - '0' == 2 && numArr[x + 1] - '0' == 0) {
                    result.append("Twenty");
                    return result.toString();
                } else {
                    int i = (numArr[x] - '0');
                    if (i > 0) {
                        result.append(TENS[i]).append(" ");
                    } else {
                        result.append("");
                    }
                    ++x;
                    if (numArr[x] - '0' != 0) {
                        result.append(ONES[numArr[x] - '0']);
                    }
                }
            }
            ++x;
        }
        return result.toString();
    }
}
