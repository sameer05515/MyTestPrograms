package com.prem.java8.streams;// Java program to find first
// element of a Stream in Java

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FindFirstFromStream {

    /**
     * Function to find the first element in a Stream.
     * 
     * @param stream The stream to find first element from
     * @return First element or null if stream is empty
     */
    public static <T> T firstElementInStream(Stream<T> stream) {
        return stream
                // findFirst() method returns
                // the first element of stream
                .findFirst()
                // if stream is empty
                // null is returned
                .orElse(null);
    }

    /**
     * Function to find the last element in a Stream.
     * Note: This method assumes N is valid and within stream bounds.
     * 
     * @param stream The stream to find last element from
     * @param N The total number of elements (should be >= 1)
     * @return Last element or null if stream is empty or N <= 0
     */
    public static <T> T lastElementInStream(Stream<T> stream, int N) {
        // Validate input
        if (N <= 0) {
            return null;
        }

        return stream
                // This returns a stream after
                // removing first N-1 elements
                // resultant stream will contain
                // only single element
                .skip(N - 1)
                // findFirst() method returns
                // the first element of
                // newly generated stream
                .findFirst()
                // if stream is empty
                // null is returned
                .orElse(null);
    }

    // Driver code
    public static void main(String[] args) {
        // Create list to avoid stream reuse issues
        List<String> strings = Arrays.asList("Geek_First", "Geek_2",
                "Geek_3", "Geek_4",
                "Geek_Last");

        // Create separate stream for first element
        Stream<String> stream1 = strings.stream();
        // Print the first element of a Stream
        System.out.println("First Element: " + firstElementInStream(stream1));

        // Create separate stream for last element
        Stream<String> stream2 = strings.stream();
        // Print the last element of a Stream
        System.out.println("Last Element: " + lastElementInStream(stream2, strings.size()));
    }
}
