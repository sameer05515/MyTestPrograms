package com.prem.java8.streams;// Java program to convert Stream to ArrayList
// using Collectors.toCollection() method

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetArrayListFromStream {

    /**
     * Function to get ArrayList from Stream.
     * Optimized version using toCollection() to avoid intermediate List conversion.
     * 
     * @param stream The stream to convert
     * @return ArrayList containing stream elements
     */
    public static <T> ArrayList<T> getArrayListFromStream(Stream<T> stream) {
        // Direct conversion from Stream to ArrayList using toCollection()
        // This is more efficient than Stream -> List -> ArrayList
        return stream.collect(Collectors.toCollection(ArrayList::new));
    }

    // Driver code
    public static void main(String args[]) {

        Stream<Integer>
                stream = Stream.of(1, 2, 3, 4, 5);

        // Convert Stream to ArrayList in Java
        ArrayList<Integer>
                arrayList = getArrayListFromStream(stream);

        // Print the arraylist
        System.out.println("ArrayList: " + arrayList);
    }
}
