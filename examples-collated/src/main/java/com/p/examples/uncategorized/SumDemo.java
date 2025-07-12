package com.p.examples.uncategorized;

public class SumDemo {
    public static void main(String[] args) {
//        System.out.println("===========\n"+add("99", "9"));
//        System.out.println("===========\n"+add("99", "1"));
//        System.out.println("===========\n"+add("1", "1999"));
//        System.out.println("===========\n"+add("1,999,999", "1"));
//        int n = 50;
//        for (int i = 0; i <= n; i++) {
//            String third = getNthFN(i);
//            System.out.println(third);
//        }

        System.out.println(getNthFN(31) + "  " + "1,346,269");
    }

    private static String getNthFN(int n) {
        switch (n) {
            case 1:
            case 2:
                return "1";
            default:
                String first = "1";
                String second = "1";
                for (int i = 1; i <= n - 2; i++) {
                    String third = add(first, second);
                    first = second;
                    second = third;
                }
                return second;
        }
    }

    private static String add(String number, String number1) {
        String result = "";
        String a = number.replaceAll(",", "");
        String b = number1.replaceAll(",", "");

        int maxLength = a.length() > b.length() ? a.length() : b.length();

        a = addPadding(a, maxLength);
        b = addPadding(b, maxLength);

        int carry = 0;
        for (int i = a.length() - 1; i >= 0; i--) {
            int sum = getInt(a.charAt(i)) + getInt(b.charAt(i)) + carry;
            result = (sum % 10) + result;
            carry = sum / 10;
        }
        if (carry > 0) {
            result = carry + result;
        }
        return addCommas(result);
    }

    private static String addCommas(String given) {
        String res = given;

        if (res != null && res.length() > 3) {
            String a = "";
            for (int i = given.length() - 1; i >= 0; i = i - 3) {
                if (i >= 3) {
                    a = "," + res.substring(i - 2, i + 1) + a;
                } else {
                    a = res.substring(0, i + 1) + a;
                }
            }
            res = a;
        }
        return res;
    }

    private static String addPadding(String str, int maxLength) {
        String res = str;
        if (str.length() < maxLength) {
            for (int i = 0; i < (maxLength - str.length()); i++) {
                res = "0" + res;
            }
        }
        return res;
    }

    private static int getInt(char given) {
        switch (given) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                throw new NumberFormatException();
        }
    }
}
