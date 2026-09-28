package com.prem;

import java.util.Scanner;

/*
* Write a program that prints the numbers from 1 to n.

But for multiples of three, print ‘Fizz’ instead of the number, and for the multiples of five, print ‘Buzz’.
For numbers which are multiples of fifteen, print ‘FizzBuzz’
.
Example for n=5:
1
2
Fizz
4
Buzz
Example for n=7:
1
2
Fizz
4
Buzz
Fizz
7

*
* */
public class Test1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = scanner.nextInt();
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
        scanner.close();
    }
}
