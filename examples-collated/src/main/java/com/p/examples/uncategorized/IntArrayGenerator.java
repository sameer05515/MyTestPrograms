package com.p.examples.uncategorized;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * The IntArrayGenerator class provides a method to generate an array of integers with a specified length.
 */
public class IntArrayGenerator {

    /**
     * The main method is the entry point of the program. It generates an array of length 10 with random integers
     * and prints the array.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        int[] randomArray = generateIntArray(10);
        
        // Printing the generated array
        System.out.println("Generated array: " + Arrays.toString(randomArray));
    }

    /**
     * Generates an array of integers with the specified length filled with random values.
     *
     * @param length The length of the array to generate.
     * @return An array of integers filled with random values.
     */
    public static int[] generateIntArray(int length) {
        Random random = new Random();
        return IntStream.generate(() -> random.nextInt(100)) // limit range to 0-99 for easier reading
                .limit(length)
                .toArray();
    }
}
