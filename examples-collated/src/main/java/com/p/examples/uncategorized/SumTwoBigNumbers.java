package com.p.examples.uncategorized;

import java.util.regex.Pattern;

public class SumTwoBigNumbers {

    public static void main(String[] args) {
        String a = "10,000";
        String b = "9";
        System.out.println(a + "\n" + b);
        System.out.println("sum: " + sum(a, b));
        System.out.println(a +" is "+(Pattern.matches(regex, a)?"valid":"invalid"));
    }

    private static String regex = "\\d+(,\\d+)?";

    private static String sum(String a, String b) {
        String result = "";
        // check if string contains integer charaters
        if (Pattern.matches(regex, a) && Pattern.matches(regex, b)) {
        a= a.replaceAll(",","");
        b= b.replaceAll(",","");
            int maxLength = a.length() > b.length() ? a.length() : b.length();
            // Add leading zeros to make both strings same length
            a = String.format("%" + maxLength + "s", a).replace(' ', '0');
            b = String.format("%" + maxLength + "s", b).replace(' ', '0');

            int carry = 0;
            for (int i = a.length() - 1; i >= 0; i--) {
                // Get the character at 'i' index and convert to integer
                int charA = Integer.parseInt(a.charAt(i) + "");
                int charB = Integer.parseInt(b.charAt(i) + "");
                result = ((charA + charB + carry) % 10) + result;
                carry = (charA + charB + carry) / 10;
            }
            if (carry > 0) {
                result = carry + result;
            }
        } else {
            System.out.println("Not valid input: " + a + " b: " + b);
        }
        String s = "";

        for (int i = result.length() - 1; i >= 0; i = i - 3) {
            if (i >= 3) {
                s = "," + result.substring(i - 2, i + 1) + s;
            } else {
                s = result.substring(0, i + 1) + s;
            }
        }
        return s;
    }
}
