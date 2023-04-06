package com.p.examples.util;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DataTypeFitter {

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        long x = scanner.nextLong();
//        System.out.println(x + " can be fitted in:");
//        printFittedTypes(x);
//    }

    public void printFittedTypes(long x) {
        if (x >= Byte.MIN_VALUE && x <= Byte.MAX_VALUE) System.out.println("* byte");
        if (x >= Short.MIN_VALUE && x <= Short.MAX_VALUE) System.out.println("* short");
        if (x >= Integer.MIN_VALUE && x <= Integer.MAX_VALUE) System.out.println("* int");
        if (x >= Long.MIN_VALUE && x <= Long.MAX_VALUE) System.out.println("* long");
        else System.out.println(x + " can't be fitted anywhere.");
    }
}
