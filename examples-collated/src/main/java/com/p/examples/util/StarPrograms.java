package com.p.examples.util;

public class StarPrograms {
    public static void main(String[] args) {
//        printNumberInRows(5);
//        printStarsFromMiddle(5);
//        printRightTriangle(5);
        printSimpleTriangle(5);
    }

    private static void printSimpleTriangle(int rowCount) {
        int counter = 1;
        for (int j = 1; j <= rowCount; j++) { // print one row

            // print elements of row
            for (int k = 1; k <= rowCount - j; k++) {
                System.out.print(" \t");
            }
            for (int k = 0; k < j; k++) {
                System.out.print(counter++ + "\t");
            }
            System.out.println();
        }
    }

    public static void printRightTriangle(int numberOfRows) {
        int i, j;
        for (i = 0; i < numberOfRows; i++) //outer loop for number of rows(n)
        {
            for (j = 2 * (numberOfRows - i); j >= 0; j--) // inner loop for spaces
            {
                System.out.print(" "); // printing space
            }
            for (j = 0; j <= i; j++) //  inner loop for columns
            {
                System.out.print("* "); // print star
            }
            System.out.println(); // ending line after each row
        }
    }

    private static void printStarsFromMiddle(int numberOfRows) {

        for (int i = 0; i < numberOfRows; i++) //outer loop for number of rows(n)
        {
            for (int j = numberOfRows - i; j > 1; j--) //inner loop for spaces
            {
                System.out.print(" "); //print space
            }
            for (int j = 0; j <= i; j++) //inner loop for number of columns
            {
                System.out.print("* "); //print star
            }

            System.out.println(); //ending line after each row
        }
    }

    private static void printNumberInRows(int numberOfRows) {
        int counter = 1;
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(counter++ + " ");
            }
            System.out.println();
        }
    }
}
