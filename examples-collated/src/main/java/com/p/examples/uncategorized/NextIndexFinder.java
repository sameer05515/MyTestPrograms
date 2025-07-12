package com.p.examples.uncategorized;

import java.util.Scanner;
import java.util.Arrays;

/**
 * The NextIndexFinder class provides a method to find the next index in a given array
 * of 10 integers using a bit-wise AND operation to ensure the index falls within
 * the valid range of array indices.
 */
public class NextIndexFinder {

    /**
     * The main method is the entry point of the program. It initializes an array of 10 integers,
     * takes user input for the array elements and the current index, and then calculates and
     * prints the next index.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int[] array = new int[10];
//
//        // Initializing the array with user input
//        System.out.println("Enter 10 integers:");
//        for (int i = 0; i < array.length; i++) {
//            array[i] = scanner.nextInt();
//        }

        int[] array= IntArrayGenerator.generateIntArray(10);
        // Printing the generated array
        System.out.println("Generated array: " + Arrays.toString(array));

//        System.out.println("Enter the current index (0-9):");
//        int currentIndex = scanner.nextInt();
        int currentIndex = 5;
        
        if (currentIndex < 0 || currentIndex >= array.length) {
            System.out.println("Invalid index. Please enter an index between 0 and 9.");
            return;
        }

        // Finding the next index using bit-wise AND operation
        int nextIndex = findNextIndex(array.length, currentIndex);
        
        System.out.println("Current index: " + currentIndex);
        System.out.println("Next index: " + nextIndex);
    }

    /**
     * Finds the next index in an array of a given length using a bit-wise AND operation.
     *
     * @param arrayLength The length of the array.
     * @param currentIndex The current index in the array.
     * @return The next index in the array, ensuring it falls within the valid range.
     */
    public static int findNextIndex(int arrayLength, int currentIndex) {
        return (currentIndex + 1 + arrayLength) % (arrayLength);
    }
}
