package com.p.examples.util;

import org.springframework.stereotype.Component;

@Component
public class StringAdder {

    public String add(String firstNumber, String secondNumber) {
        String calculation = "";
        int carry = 0;
        int longestLength = Math.max(firstNumber.length(), secondNumber.length());
        firstNumber = addPadding(longestLength, '0', firstNumber);
        secondNumber = addPadding(longestLength, '0', secondNumber);
        
        for (int i = longestLength - 1; i >= 0; i--) {
            int sum = Character.getNumericValue(firstNumber.charAt(i)) +
                      Character.getNumericValue(secondNumber.charAt(i)) +
                      carry;
            calculation = (sum % 10) + calculation;
            carry = sum / 10;
        }
        
        if (carry > 0) {
            calculation = carry + calculation;
        }
        
        return calculation;
    }

    private String addPadding(int length, char replaceChar, String inputString) {
        return String.format("%1$" + length + "s", inputString).replace(' ', replaceChar);
    }
}
