package com.p.currencyformatter;

public class AddPaddingInString {
    public static void main(String[] args) {
        String res = add("10", "0");
        System.out.println(res);
    }

    public static String add(String firstNumber, String secondNumber) {

        String calculation = "";
        int carry = 0;
        int sum = 0;

        int longestLength = firstNumber.length() > secondNumber.length() ? firstNumber.length() : secondNumber.length();
        firstNumber = addPad(longestLength, '0', firstNumber);
        secondNumber = addPad(longestLength, '0', secondNumber);
        String[] fStr = firstNumber.split("");
        String[] sStr = secondNumber.split("");
        System.out.println("---------------------------");
        for (String s : fStr) {
            System.out.println(s);
        }
        System.out.println("---------------------------");
        for (String s : sStr) {
            System.out.println(s);
        }
        System.out.println("---------------------------");
        System.out.println(firstNumber + "\n" + secondNumber);
        for (int i = longestLength - 1; i >= 0; i--) {
            sum = Integer.parseInt(fStr[i]) + Integer.parseInt(sStr[i]) + carry;
            calculation = (sum % 10) + calculation;
            carry = sum / 10;

            System.out.printf("%s + %s = %s, carry = %s%n", fStr[i], sStr[i], sum, carry);
        }
        if (carry > 0) {
            calculation = carry + calculation;
        }

        return calculation;
    }

    public static String addPad(int length, char replaceChar, String inputString) {
        return String.format("%1$" + length + "s", inputString).replace(' ', replaceChar);
    }
}
