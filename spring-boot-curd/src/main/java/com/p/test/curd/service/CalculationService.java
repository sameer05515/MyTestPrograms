package com.p.test.curd.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.p.test.curd.util.GlobalConstants.regexToCheckIntegerOnly;

/**
 * defining the business logic
 */
@Service
public class CalculationService {

    public String sum(String firstNumber, String secondNumber) {
        String calculation = "";
        int carry = 0;
        int sum;
        if (!StringUtils.isEmpty(firstNumber) && !StringUtils.isEmpty(secondNumber)) {
            if (firstNumber.matches(regexToCheckIntegerOnly) && secondNumber.matches(regexToCheckIntegerOnly)) {
                int longestLength = firstNumber.length() > secondNumber.length() ? firstNumber.length()
                        : secondNumber.length();
                firstNumber = addPad(longestLength, '0', firstNumber);
                secondNumber = addPad(longestLength, '0', secondNumber);
                String[] fStr = firstNumber.split("");
                String[] sStr = secondNumber.split("");
                for (int i = longestLength - 1; i >= 0; i--) {
                    sum = Integer.parseInt(fStr[i]) + Integer.parseInt(sStr[i]) + carry;
                    calculation = (sum % 10) + calculation;
                    carry = sum / 10;
                    System.out.printf("%s + %s = %s, carry = %s%n", fStr[i], sStr[i], sum, carry);
                }
                if (carry > 0) {
                    calculation = carry + calculation;
                }
            }
        }
        return calculation;
    }

    private String addPad(int length, char replaceChar, String inputString) {
        return String.format("%1$" + length + "s", inputString).replace(' ', replaceChar);
    }
}
