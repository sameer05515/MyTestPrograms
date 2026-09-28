package com.p.java.playground.api.array.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * The RandomArrayService class provides methods to generate random integer arrays.
 */
@Service
public class ArrayService {

    /**
     * Generates an array of random integers with the specified length.
     *
     * @param length The length of the array to generate.
     * @return An array of random integers.
     * @throws IllegalArgumentException if the length is less than or equal to zero.
     */
    public int[] generateRandomArray(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive integer.");
        }
        Random random = new Random();
        return IntStream.generate(() -> random.nextInt(100)) // Random integers between 0 and 99
                .limit(length)
                .toArray();
    }

    /**
     * Finds the next index in an array of a given length using a bit-wise AND operation.
     *
     * @param arrayLength  The length of the array.
     * @param currentIndex The current index in the array.
     * @return The next index in the array, ensuring it falls within the valid range.
     */
    public int findNextIndex(int arrayLength, int currentIndex) {
        if (arrayLength <= 0 || currentIndex < 0 || currentIndex >= arrayLength) {
            throw new IllegalArgumentException(String.format(
                    "Invalid arrayLength (%d) or currentIndex (%d).", arrayLength, currentIndex));
        }
        return (currentIndex + 1 + arrayLength) % arrayLength;
    }
}
