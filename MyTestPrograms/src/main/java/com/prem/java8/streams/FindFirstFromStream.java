package com.prem.java8.streams;// Java program to find first
// element of a Stream in Java

import java.util.stream.Stream;

public class FindFirstFromStream {

    // Function to find the
    // first_elements in a Stream
    public static <T> T
    firstElementInStream(Stream<T> stream) {
        return stream
                // findFirst() method returns
                // the first element of stream
                .findFirst()
                // if stream is empty
                // null is returned
                .orElse(null);
    }

    // Function to find the
    // last_elements in a Stream
    public static <T> T
    lastElementInStream(Stream<T> stream, int N) {

        return stream

                // This returns a stream after
                // removing first N-1 elements
                // resultant stream will contain
                // only single element
                .skip(N - 1)

                // findFirst() method return
                // the first element of
                // newly generated stream
                .findFirst()

                // if stream is empty
                // null is returned
                .orElse(null);
    }

    // Driver code
    public static void main(String[] args) {

        Stream<String> stream
                = Stream.of("Geek_First", "Geek_2",
                "Geek_3", "Geek_4",
                "Geek_Last");

        // Print the first element of a Stream
        System.out.println(
                "First Element: "
                        + firstElementInStream(stream));
    }
}
